package sptest.controller

import java.sql.DriverManager

import com.alibaba.fastjson.JSONObject
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RestController}

@RestController
class IndexController {
  Class.forName("com.facebook.presto.jdbc.PrestoDriver")
  def presto(sql:String):String={
      val connection=DriverManager.getConnection("jdbc:presto://node1:9999/hive/default","hive",null)
      val statement=connection.prepareStatement(sql)
      val rs=statement.executeQuery()
      //获取列数
      val colnums = rs.getMetaData().getColumnCount
      var tittle = ""
      var row = ""
      for(i <- Array.range(1, colnums + 1)){ tittle += "<th>"+rs.getMetaData.getColumnName(i)+"</th>"}
//      tittle = "<tr>" +tittle + "<tr>"
      while(rs.next()){
        //列索引从1开始
        for(i <- Array.range(1, colnums + 1)){
          row += "<td>"+ s"${rs.getString(i)}" + "</td>"
//          print(s"${rs.getMetaData.getColumnLabel(i)}:${rs.getString(i)} \t ")
        }
        row = "<tr>" + row + "</tr>"
      }
     """<html><body><table border="1">""" + tittle +  row +  "</table></body></html>"
  }


  @RequestMapping(value = Array("/index"),method = Array(RequestMethod.GET))
  def index(sql:String):String= {
//      val json = new JSONObject
//      json.put("code", 0)
//      json.put("data", "success")
//      json
    presto(sql)
  }

  @RequestMapping(value = Array("/spark"),method = Array(RequestMethod.GET))
  def spark(id:String):String= {
    if(id.length < 3){
      "id 错误"
    }else{
      val rt = Runtime.getRuntime()
      try{
        rt.exec(s"spark-submit --master spark://node1:7077 --total-executor-cores 1 /opt/sparkCode/tangli/spark-warehouse/spark-warehouse/bicodev2art.jar ${id}")
      }catch {
        case ex : Throwable => println("found a unknown exception"+ ex)
      }
      s"run ${id}"
    }

    //      val json = new JSONObject
    //      json.put("code", 0)
    //      json.put("data", "success")
    //      json

  }

}

