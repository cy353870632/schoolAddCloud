package com.github.wxiaoqi.security.hrsystem.utils;

import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;

import java.io.IOException;
import java.util.*;

public class ArrayListType {
    public Map<String,ArrayList>  sort(List list){
        TreeMap tm=new TreeMap();
        for(int i=0;i<list.size();i++){
            PanelVo s=(PanelVo)list.get(i);
            if(tm.containsKey(s.getColGroup())){//
                ArrayList l11=(ArrayList)tm.get(s.getColGroup());
                l11.add(s);
            }else{
                ArrayList tem=new ArrayList();
                tem.add(s);
                tm.put(s.getColGroup(), tem);
            }

        }
        return tm;
    }
    public static void main(String[] args) throws IOException{

//        Map<String,ArrayList> ss =new ArrayListType().sort(list);
//        Iterator it = ss.keySet().iterator();
//        while(it.hasNext()){
//            String key = (String)it.next();
//            System.out.print("\n"+key+":");
//            ArrayList list1 = ss.get(key);
//            for(int i=0; i<list1.size(); i++){
//                SupplierBean sb = (SupplierBean)list1.get(i);
//                System.out.print("   "+sb.getName1());
//            }
//
//        }
    }
}
