package org.apache.lucene.demo;


import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.spell.JaroWinklerDistance;
/**
 * 运行此段代码前，先要执行indexfiles.java
 */
public class Spellchecker {
  
  private Spellchecker() {}

  public static void main(String[] args) {
      Directory directory;
    try {
	//设定索引目录spellIndexDirectory,此目录是目标词的索引，如果目标索引里有一致的词则直接返回，
	//如果没有的话则通过下面的词典进行distance的计算，返回一个推荐
	directory = FSDirectory.open(new File("c:\\luceneindexc"));
	//读一个索引文件，然后返回一个indexreader
	IndexReader indexreader= IndexReader.open(directory);
	//初始化speller,设定使用jarowinkler算法
	SpellChecker spellchecker = new SpellChecker(directory,new JaroWinklerDistance());
	//对词典进行索引
	spellchecker.indexDictionary(new PlainTextDictionary(new File("c:\\dictionary.txt")));
	//word, numSug, ir, field, morePopular, accuracy
	//word为要check的值
	//numSug为推荐个数
	//如果morePopular为true则只返回suggestion的值，而不返回索引里的值
	//ir为indexreader
	//field为要check的值在索引里是按照什么字段索引的
//	String[] suggestions=spellchecker.suggestSimilar("Chine",5,indexreader,"contents",false);
	String[] suggestions=spellchecker.suggestSimilarWordsAndFilepathByWilliam("che",5,indexreader,"contents",false,0.5f);
	for(String str:suggestions){
	    System.out.println(str);
	}
    } catch (IOException e) {
	e.printStackTrace();
    };
      
  }
}
