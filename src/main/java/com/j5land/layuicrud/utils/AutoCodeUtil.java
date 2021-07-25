package com.j5land.layuicrud.utils;

import cn.hutool.core.date.DateTime;
import com.j5land.layuicrud.model.auto.TableInfo;
import com.j5land.layuicrud.model.request.AutoConfigModel;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 自动生成 通用类
 *
 */
public class AutoCodeUtil {

	public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        //java代码模板
        templates.add("auto_template/model/Entity.java.vm");
        templates.add("auto_template/model/EntityCriteria.java.vm");
        templates.add("auto_template/mapperxml/EntityMapper.xml.vm");
        templates.add("auto_template/service/EntityService.java.vm");
        templates.add("auto_template/mapper/EntityMapper.java.vm");
        templates.add("auto_template/controller/EntityController.java.vm");
        //前端模板
        templates.add("auto_template/html/list.html.vm");
        templates.add("auto_template/html/add.html.vm");
        templates.add("auto_template/html/edit.html.vm");
        return templates;
    }
	
	
	/**
	 * 创建单表
	 * @param tableName 表名
	 * @param conditionQueryField  条件查询字段
	 * @param pid 父id
	 * @param sqlcheck 是否录入数据
	 * @param vhtml 生成html
	 * @param vController 生成controller
	 * @param vservice 生成service
	 * @param vMapperORdao 生成mapper or dao
	 */
	public static void autoCodeOneModel(TableInfo tableInfo, AutoConfigModel autoConfigModel){
		AutoCodeProperties autoCodeConfig = new AutoCodeProperties();
		//设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
		Map<String, Object> map = new HashMap<>();
        //数据库表数据
		map.put("tableInfo",tableInfo);
        //字段集合
        map.put("beanColumns",tableInfo.getBeanColumns());
        //配置文件
        map.put("SnowflakeIdWorker", SnowflakeIdWorker.class);
        //class类路径
        map.put("parentPack", autoCodeConfig.getConfigkey("parentPack"));
        //作者
        map.put("author", autoConfigModel.getAuthor());
        //时间
        map.put("datetime",new DateTime());
        //sql需要的权限父级pid
        map.put("pid",autoConfigModel.getPid());
        
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
        List<String> templates = getTemplates();
		/*
		 * if(vhtml!=true) { templates.remove("auto_code/html/list.html.vm");
		 * templates.remove("auto_code/html/add.html.vm");
		 * templates.remove("auto_code/html/edit.html.vm"); } if (vController!=true) {
		 * templates.remove("auto_code/controller/EntityController.java.vm"); } if
		 * (vService!=true) {
		 * templates.remove("auto_code/service/EntityService.java.vm"); } if
		 * (vMapperORdao!=true) { templates.remove("auto_code/model/Entity.java.vm");
		 * templates.remove("auto_code/model/EntityCriteria.java.vm");
		 * templates.remove("auto_code/mapperxml/EntityMapper.xml.vm");
		 * templates.remove("auto_code/mapper/EntityMapper.java.vm"); }
		 */
        for (String template : templates) {
        	try {
        			String targetPath = autoConfigModel.getParentPath();
        			String filepath=getCoverFileName(template,tableInfo ,autoCodeConfig.getConfigkey("parentPack"),targetPath);
    		        Template tpl = Velocity.getTemplate(template, "UTF-8" );
    				File file = new File(filepath);
    				if (!file.getParentFile().exists())
    		            file.getParentFile().mkdirs();
    		        if (!file.exists())
    		            file.createNewFile();
					try (FileOutputStream outStream = new FileOutputStream(file);
						 OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
						 BufferedWriter sw = new BufferedWriter(writer)) {
						 tpl.merge(context, sw);
						 sw.flush();
						 System.out.println("成功生成Java文件:" + filepath);
					}
        	} catch (IOException e) {
                try {
					throw new Exception("渲染模板失败，表名：" +"c"+"\n"+e.getMessage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        }
	}

	/**
	 * 预览方法
	 * @param tableInfo 数据库表
	 * @return
	 */
	public static Map<String,String> viewAuto(TableInfo tableInfo,AutoConfigModel autoConfigModel){
		Map<String, String> velocityMap=new HashMap<String, String>();

		AutoCodeProperties properties = new AutoCodeProperties();
		//设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
		Map<String, Object> map = new HashMap<>();
		//数据库表数据
		map.put("tableInfo",tableInfo);
        //字段集合
        map.put("beanColumns",tableInfo.getBeanColumns());
        //配置文件
        map.put("SnowflakeIdWorker", SnowflakeIdWorker.class);
        //class类路径
        map.put("parentPack", properties.getConfigkey("parentPack"));
        //作者
        map.put("author", autoConfigModel.getAuthor());
        //时间
        map.put("datetime",new DateTime());
        //sql需要的权限父级pid
        map.put("pid",autoConfigModel.getPid());
        
        VelocityContext velocityContext = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
			Template tpl = Velocity.getTemplate(template, "UTF-8" );
			StringWriter sw = new StringWriter(); 
			tpl.merge(velocityContext, sw);
			System.out.println("输出模板");
			System.out.println(sw);
			System.out.println("输出模板 end");
			velocityMap.put(template.substring(template.lastIndexOf("/")+1, template.lastIndexOf(".vm")), sw.toString());
        }
        return velocityMap;
	}

	/**
	 * 自动生成压缩文件方法
	 * @param tableInfo
	 * @param zip
	 */
	public static void autoCodeOneModel(TableInfo tableInfo,AutoConfigModel autoConfigModel,ZipOutputStream zip){
		AutoCodeProperties properties = new AutoCodeProperties();
		//设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
		Map<String, Object> map = new HashMap<>();
        //数据库表数据
		map.put("tableInfo",tableInfo);
        //字段集合
        map.put("beanColumns",tableInfo.getBeanColumns());
        //配置文件
        map.put("SnowflakeIdWorker", SnowflakeIdWorker.class);
        //class类路径
        map.put("parentPack", properties.getConfigkey("parentPack"));
        //作者
        map.put("author", autoConfigModel.getAuthor());
        //时间
        map.put("datetime",new DateTime());
        //sql需要的权限父级pid
        map.put("pid",autoConfigModel.getPid());
        VelocityContext velocityContext = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
        	try {
					String filepath=getCoverFileName(template,tableInfo ,properties.getConfigkey("parentPack"),"");
					Template tpl = Velocity.getTemplate(template, "UTF-8" );
					StringWriter sw = new StringWriter();
					tpl.merge(velocityContext, sw);
					zip.putNextEntry(new ZipEntry(filepath));
					IOUtils.write(sw.toString(), zip, "UTF-8");
					IOUtils.closeQuietly(sw);
					zip.closeEntry();
        	} catch (IOException e) {
                try {
					throw new Exception("渲染模板失败，表名：" +"c"+"\n"+e.getMessage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        }
	}
	
	

	/**
	 * 
	 * @param template
	 * @param classname
	 * @param className
	 * @param packageName
	 * @param moduleName
	 * @param controller
	 * @return
	 */
    public static String getCoverFileName(String template,TableInfo tableInfo, String packageName,String targetPath) {
    	String separator = File.separator;
    	String packagePath =targetPath+separator+"src"+separator + "main" + separator + "java" + separator;
        String resourcesPath=targetPath+separator+"src"+separator + "main" + separator+"resources"+ separator;;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", separator) + separator;
        }

        if (template.contains("Entity.java.vm")) {//model.java
            return packagePath+"model" +separator+ "auto" + separator + tableInfo.getJavaTableName() + ".java";
        }
        if(template.contains("EntityCriteria.java.vm")) {//modelCriteria.java
        	return packagePath+"model" +separator+ "auto" + separator + tableInfo.getJavaTableName() + "Criteria.java";
        }
        
        if (template.contains("EntityMapper.java.vm")) {//daomapper.java
            return packagePath + "mapper" + separator + tableInfo.getJavaTableName() + "Mapper.java";
        }
        if (template.contains("EntityMapper.xml.vm")) {//daomapper.xml
            return resourcesPath+"mybatis" + separator + tableInfo.getJavaTableName() + "Mapper.xml";
        }
        
        if (template.contains("EntityService.java.vm")) {
            return packagePath + "service" + separator + tableInfo.getJavaTableName() + "Service.java";
        }
        if(template.contains("EntityController.java.vm")) {
        	 return packagePath + "controller" + separator + tableInfo.getJavaTableName() + "Controller.java";
        }
        if(template.contains("list.html.vm")) {
        	 return resourcesPath+"templates"+separator + "auto"+separator + tableInfo.getJavaTableName_a()+separator +"list.html";
        }
        if(template.contains("add.html.vm")) {
        	System.err.println(resourcesPath+"templates"+separator + "auto"+separator + tableInfo.getJavaTableName_a()+separator );
       	 	 return resourcesPath+"templates"+separator + "auto"+separator + tableInfo.getJavaTableName_a()+separator +"add.html";
        }
        if(template.contains("edit.html.vm")) {
       	 	return  resourcesPath+"templates"+separator + "auto"+separator + tableInfo.getJavaTableName_a()+separator +"edit.html";
        }
        return "";
    }
}
