package com.Test;

import java.math.BigDecimal;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.ToneThink.DataTable.DataTable;
import com.ToneThink.DateTime.DateTime;
import com.ToneThink.ctsTools.dbHelper.my_odbc;
import com.ToneThink.ctsTools.myUtility.pmList;
import com.ToneThink.ctsTools.myUtility.pmMap;

public class abc {

	@Test
	public void ss() {
	/*	int max=9999;
        int min=1000;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s);
		*/
	//System.out.println(random.nextInt(100));
	
		//System.out.println("测试打印");
		//System.out.println(fun_read_properties.strCtiUrl);
		
	System.out.println(DateTime.Now().getMonth());	
	System.out.println(DateTime.Now().ToString("MM"));	
	}

	@Test
	public void ssss()
	{
		 
		
	}
	
}
