package com.ot.lucene.test;

import com.ot.lucene.lucene.dao.SkuDao;
import com.ot.lucene.lucene.dao.impl.SkuDaoImpl;
import com.ot.lucene.lucene.pojo.Sku;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.RangeQueryBuilder;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * 索引管理
 */
public class IndexManager {

    private static final String INDEX_PATH = "D:\\software\\luceneindex";


    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        //1.收集数据
        SkuDao dao = new SkuDaoImpl();
        List<Sku> skuList = dao.findAll();
        //2.创建document对象
        List<Document> documentList = new ArrayList<>();
        for (Sku sku : skuList) {
            Document document = new Document();
            //3.在document对象当中添加field域
            // 商品id 不分词，不索引，存储
            document.add(new StringField("id", sku.getId().toString(), Field.Store.YES));
            // 商品名称 分词，索引，存储
            document.add(new TextField("name", sku.getName(), Field.Store.YES));
            // 商品价格 不分词，索引，存储
            document.add(new FloatPoint("price", sku.getPrice()));
            document.add(new StoredField("price", sku.getPrice()));
            // 品牌名称 不分词，索引，存储
            document.add(new StringField("brandName", sku.getBrandName(), Field.Store.YES));
            // 分类名称 不分词，索引，存储
            document.add(new StringField("categoryName", sku.getCategoryName(), Field.Store.YES));
            // 图片地址 不分词，不索引，存储
            document.add(new StoredField("image", sku.getImage()));
            documentList.add(document);
        }
        long start = System.currentTimeMillis();
        //4.创建分词器,分析文档，进行分词
        IKAnalyzer analyzer = new IKAnalyzer();
        //5.创建directory对象，声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //6.创建indexWriterConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //控制一个新的segment写入内存前保存的document的数目，设置较大数目可以加快索引，同时也会消耗内存，数目大，减少io次数
//        config.setMaxBufferedDocs(10000);
        //7.创建indexWriter写入对象
        IndexWriter writer = new IndexWriter(directory, config);

        //设置n个segment合并，数值越大索引越快，搜索越慢，值越小索引越慢，搜索越快
//        writer.forceMerge(100);
        //8.写入到索引库
        for (Document document : documentList) {
            writer.addDocument(document);
        }
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "s");
    }

    @Test
    public void search() throws ParseException, IOException {
        //1.创建搜索对象
//        StandardAnalyzer analyzer = new StandardAnalyzer();
        IKAnalyzer ikAnalyzer = new IKAnalyzer();
        //第一个参数是搜索域，如果没有，则默认搜索域
        QueryParser queryParser = new QueryParser("brandName", ikAnalyzer);
        Query query = queryParser.parse("name:手机 AND 华为");
        //2.声明索引库的位置
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //3.创建索引读取对象
        IndexReader reader = DirectoryReader.open(directory);
        //4.创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        //5.使用索引搜索对象，执行搜索，返回结果集
        TopDocs docs = searcher.search(query, 10);
        System.out.println("查询到的总数是：" + docs.totalHits);
        //6.获取查询结果集
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        //7.解析结果集
        for (ScoreDoc doc : scoreDocs) {
            int docID = doc.doc;
            Document document = searcher.doc(docID);
            System.out.println("=============================");
            System.out.println("docID:" + docID);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    /**
     * 更新索引，是先查找然后删除然后在添加
     */
    @Test
    public void update() throws IOException {
        //创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        //创建流对象
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        Document document = new Document();
        document.add(new TextField("id", "1234567", Field.Store.YES));
        document.add(new TextField("name", "lucene测试", Field.Store.YES));

        //执行更新，会把符合条件的document删除然后再新增
        writer.updateDocument(new Term("id", "1234567"), document);
        writer.close();
    }

    /**
     * 删除索引
     *
     * @throws IOException
     */
    @Test
    public void delete() throws IOException {
        //创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        //创建流对象
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        writer.deleteAll();
        writer.close();
    }

    /**
     * 测试 whitespaceAnalyzer分词器
     */
    @Test
    public void testWhitespaceAnalyzer() throws IOException {
        //1.创建分词器
        WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
        //2.指定索引路径
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //3.声明配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4.创建indexWriter
        IndexWriter writer = new IndexWriter(directory, config);
        //5.创建文档
        Document document = new Document();
        document.add(new TextField("name", "vivo X23 8GB+128GB 幻夜蓝", Field.Store.YES));
        writer.addDocument(document);
        writer.close();
    }

    /**
     * 测试 分词器
     */
    @Test
    public void testSimpleAnalyzer() throws IOException {
        //1.创建分词器
        SimpleAnalyzer analyzer = new SimpleAnalyzer();
        //2.指定索引路径
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //3.声明配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4.创建indexWriter
        IndexWriter writer = new IndexWriter(directory, config);
        //5.创建文档
        Document document = new Document();
        document.add(new TextField("name", "vivo，X23。 8GB+128GB； 幻夜蓝", Field.Store.YES));
        writer.addDocument(document);
        writer.close();
    }

    /**
     * 测试 IK中文分词器
     */
    @Test
    public void testIKAnalyzer() throws IOException {
        //1.创建分词器
        IKAnalyzer analyzer = new IKAnalyzer();
        //2.指定索引路径
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //3.声明配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4.创建indexWriter
        IndexWriter writer = new IndexWriter(directory, config);
        //5.创建文档
        Document document = new Document();
        TextField name = new TextField("name",
                "vivo X23 8GB+128GB 幻夜蓝,水滴屏全面屏,游戏手机.移\n" +
                        "动联通电信全网通4G手机的了", Field.Store.YES);
        StringField id = new StringField("id", "123456", Field.Store.YES);
        document.add(name);
        document.add(id);
        document.add(new FloatPoint("price", 22.0f));
        document.add(new StoredField("price", 22.0f));
        writer.addDocument(document);
        writer.close();
    }

    /**
     * lucene高级搜索
     * <p>
     * 文本搜索
     */
    @Test
    public void search1() throws ParseException, IOException {
        //1.创建搜索对象
        IKAnalyzer analyzer = new IKAnalyzer();//brandName
        QueryParser queryParser = new QueryParser("brandName", analyzer);
        //如果parse当中存在域，则从这个域当中搜索，如果只有关键字，则从上述默认域当中搜索
        Query query = queryParser.parse("华为手机");
        //2.声明索引库位置
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //3.创建索引读取对象
        IndexReader reader = DirectoryReader.open(directory);
        //4.创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        //5.使用索引搜索对象搜索
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    /**
     * 数值范围搜索
     * <p>
     * relevance:关联性 ，按照关联性评分进行排序
     * indexorder:索引器，按照索引的顺序进行排序
     */
    @Test
    public void search2() throws IOException {
        Query query = FloatPoint.newRangeQuery("price", 100, 1000);
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        //reverse
        SortField sortField = new SortField("price", SortField.Type.SCORE, true);
        Sort sort = new Sort(sortField);
        TopDocs topDocs = searcher.search(query, 10, sort);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    /**
     * 组合查询
     * 比如说搜索价格大于等于100，小于等于1000，并且名称当中不包含华为手机关键字的商品
     */
    @Test
    public void search3() throws ParseException, IOException {
        IKAnalyzer analyzer = new IKAnalyzer();
        Query rangeQuery = FloatPoint.newRangeQuery("price", 100, 1000);
        QueryParser queryParser = new QueryParser("name", analyzer);
        Query nameQuery = queryParser.parse("华为手机");
        //创建组合查询
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(new BooleanClause(rangeQuery, BooleanClause.Occur.MUST));
        builder.add(new BooleanClause(nameQuery, BooleanClause.Occur.MUST));
        BooleanQuery booleanQuery = builder.build();
        //
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(booleanQuery, 10);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    @Test
    public void search4() throws ParseException, IOException {
        IKAnalyzer analyzer = new IKAnalyzer();
        QueryParser queryParser = new QueryParser("name", analyzer);
        Query nameQuery = queryParser.parse("运动鞋 AND 紫色 AND 40");
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(nameQuery, 10);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    @Test
    public void search5() throws ParseException, IOException {
        IKAnalyzer analyzer = new IKAnalyzer();
        //term采用关键词匹配，不支持AND等语法,建议根据情况自行选择，尽力避免大范围的日期查询
        TermQuery termQuery = new TermQuery(new Term("name", "袜"));
        //底层就是内存映射
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(termQuery, 10);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    /**
     * lucene 对查询关键字和索引文档的相似度进行打分，得分高的排在前面
     * <p>
     * 1.lucene在用户检索的实时数据根据搜索的关键词进行计算
     * a)计算出词的权重
     * b)根据词的权重，计算文档的相关得分
     * 明确索引的最小单位是term，搜索也是从term开始，根据term找到文档，term对文档的重要性成为权重，影响term权重的因素有
     * 1)term f ,term出现的次数，次数越大，越重要
     * 2)document f，指有多少文档中包含term，df越大越不重要，比如说在一篇英文文档当中，this出现的次数多，就说明越重要吗？不是的
     * 有越多的文档包含term，说明此term太普通，不足以区分这些文档，因此重要性就低
     */

    /**
     * lucene 7之后，删除了创建索引时的权重 ，影响相关度排序
     *
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void search6() throws ParseException, IOException {
        TermQuery termQuery = new TermQuery(new Term("name", "袜"));
        //使用boostQuery对查询的query包装一下，增加权重
        BoostQuery boostQuery = new BoostQuery(termQuery, 1.5f);
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(boostQuery, 10);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    /**
     * 人为影响相关度排序
     *
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void search7() throws ParseException, IOException {
        IKAnalyzer analyzer = new IKAnalyzer();
        String[] fields = {"name", "brandName", "categoryName"};
        Map<String, Float> boots = new HashMap<>();
        boots.put("brandName", 100000f);
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, analyzer, boots);
        Query query = queryParser.parse("vivo");
        Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("搜索到的总数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("=============================");
            System.out.println("docID:" + docId);
            System.out.println("id:" + document.get("id"));
            System.out.println("name:" + document.get("name"));
            System.out.println("price:" + document.get("price"));
            System.out.println("brandName:" + document.get("brandName"));
            System.out.println("categoryName:" + document.get("categoryName"));
            System.out.println("image:" + document.get("image"));
        }
    }

    @Test
    public void testTime() throws IOException {
        //1.创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        //2.指定索引路径
        FSDirectory directory = FSDirectory.open(Paths.get(INDEX_PATH));
        //3.声明配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //4.创建indexWriter
        IndexWriter writer = new IndexWriter(directory, config);
        //5.创建文档
        Document document = new Document();
        StringField field = new StringField("time", DateTools.dateToString(new Date(), DateTools.Resolution.SECOND), Field.Store.YES);
        document.add(field);
        writer.addDocument(document);
        writer.close();
    }
    /**
     * 1.关键词区分大小写 OR AND TO等关键词是区分大小写的，lucene只认大写的，小写的当做普通单词。
     * 2.读写互斥性 同一时刻只能有一个对索引的写操作，在写的同时可以进行搜索
     * 3.文件锁 在写索引的过程中强行退出将在tmp目录留下一个lock文件，使以后的写操作无法进行，可以将其手工删除
     * 4.时间格式 lucene只支持一种时间格式yyMMddHHmmss，所以你传一个yy-MM-dd HH:mm:ss的时间给lucene它是不会当作时间来处理的
     * 5.设置boost 有些时候在搜索时某个字段的权重需要大一些，例如你可能认为标题中出现关键词的文章比正文中出现关键词的文章更
     * 有价值，你可以把标题的boost设置的更大，那么搜索结果会优先显示标题中出现关键词的文章.
     */

}
