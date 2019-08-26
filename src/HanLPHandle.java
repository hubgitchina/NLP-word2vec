package com.huafa.job.task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

/**
 * @ClassName: HanLPHandle
 * @Description: TODO
 * @Author wangpeng
 * @Date 2019-8-22 14:09
 * @Version 1.0
 */
public class HanLPHandle {

    private static final String TRAIN_FILE_NAME = MSR.TRAIN_PATH;
//    private static final String MODEL_FILE_NAME = "data/test/word2vec.txt";
    private static final String MODEL_FILE_NAME = "D:\\HanLP\\data\\icwb2\\word2vec.txt";

    public static void main(String[] args) throws IOException
    {

        //训练词向量
//        Word2VecTrainer trainerBuilder = new Word2VecTrainer();
//        WordVectorModel wordVectorModel = trainerBuilder.train("D:\\HanLP\\data\\icwb2\\sgns.baidubaike.bigram-char", "D:\\HanLP\\data\\icwb2\\msr_baidu.txt");
//        System.out.println(wordVectorModel.nearest("中国"));

        long l3 = System.currentTimeMillis();

//        WordVectorModel wordVectorModel = trainOrLoadModel();
        WordVectorModel wordVectorModel = new WordVectorModel("D:\\HanLP\\data\\icwb2\\hanlp-wiki-vec-zh.txt");
//        printNearest("上海", wordVectorModel);
//        printNearest("美丽", wordVectorModel);
//        printNearest("购买", wordVectorModel);
//        System.out.println(wordVectorModel.similarity("上海", "广州"));
//        System.out.println(wordVectorModel.analogy("日本", "自民党", "共和党"));

//        System.out.println(wordVectorModel.nearest("中国"));

        // 文档向量
        DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);

        String s5 = "他去吃饭了";
        String s6 = "她在看电影";

        System.out.println(docVectorModel.similarity("山西副省长贪污腐败开庭", "陕西村干部受贿违纪"));
        System.out.println(docVectorModel.similarity("山西副省长贪污腐败开庭", "股票基金增长"));

//        String s5 = "子类可以覆盖父类的成员方法，也可以覆盖父类的成员变量";
//        String s6 = "子类不可以覆盖父类的成员方法，也不可以覆盖父类的成员变量";

        String s7 = "我们把香蕉给猴子，因为它们饿了";
        String s8 = "我们把香蕉给猴子，因为它们熟透了";

        String s1 = "世界你好";
        String s2 = "你好世界";

        System.out.println(docVectorModel.similarity(s5, s6));
        System.out.println(docVectorModel.similarity(s7, s8));
        System.out.println(docVectorModel.similarity(s1, s2));

        long l4 = System.currentTimeMillis();
        System.out.println(l4 - l3);

        String[] documents = new String[]{
                "山东苹果丰收",
                "农民在江苏种水稻",
                "奥运会女排夺冠",
                "世界锦标赛胜出",
                "中国足球失败",
        };

//        System.out.println(docVectorModel.similarity(documents[0], documents[1]));
//        System.out.println(docVectorModel.similarity(documents[0], documents[4]));

//        for (int i = 0; i < documents.length; i++)
//        {
//            docVectorModel.addDocument(i, documents[i]);
//        }
//
//        printNearestDocument("体育", documents, docVectorModel);
//        printNearestDocument("农业", documents, docVectorModel);
//        printNearestDocument("我要看比赛", documents, docVectorModel);
//        printNearestDocument("要不做饭吧", documents, docVectorModel);
    }

    static void printNearest(String word, WordVectorModel model)
    {
        System.out.printf("\n                                                Word     Cosine\n------------------------------------------------------------------------\n");
        for (Map.Entry<String, Float> entry : model.nearest(word))
        {
            System.out.printf("%50s\t\t%f\n", entry.getKey(), entry.getValue());
        }
    }

    static void printNearestDocument(String document, String[] documents, DocVectorModel model)
    {
        printHeader(document);
        for (Map.Entry<Integer, Float> entry : model.nearest(document))
        {
            System.out.printf("%50s\t\t%f\n", documents[entry.getKey()], entry.getValue());
        }
    }

    private static void printHeader(String query)
    {
        System.out.printf("\n%50s          Cosine\n------------------------------------------------------------------------\n", query);
    }

//    static WordVectorModel trainOrLoadModel() throws IOException
//    {
//        if (!IOUtil.isFileExisted(MODEL_FILE_NAME))
//        {
//            if (!IOUtil.isFileExisted(TRAIN_FILE_NAME))
//            {
//                System.err.println("语料不存在，请阅读文档了解语料获取与格式：https://github.com/hankcs/HanLP/wiki/word2vec");
//                System.exit(1);
//            }
//            Word2VecTrainer trainerBuilder = new Word2VecTrainer();
//            return trainerBuilder.train(TRAIN_FILE_NAME, MODEL_FILE_NAME);
//        }
//
//        return loadModel();
//    }
//
//    static WordVectorModel loadModel() throws IOException
//    {
//        return new WordVectorModel(MODEL_FILE_NAME);
//    }
}
