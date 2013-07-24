package com.flywet.platform.dk.repjsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 根据jsp中的替换模板标签进行jsp的自动生成
 * 生成的jsp页面与原页面在同一目录下，
 * 命名规则为替换掉原文件名中的_dev后缀
 * * @author han
 * @goal replace
 *
 */
public class DIRepjspPlugin extends AbstractMojo {
	/**
	 * @parameter
	 * @required
	 */
	private List<String> includes;
	/**
	 * @parameter expression="${encoding}" default-value="UTF-8"
	 */
	private String encoding;
	
	/**
	 * @parameter expression="${project.basedir}"
	 * @required
	 * @readonly
	 */
	private File basedir;
	
	/**
	 * @parameter expression="${project.build.directory}"
	 * @readonly
	 */
	private File builddir;
	
	/**
	 * @parameter
	 * @required
	 */
	private String webAppDir;
	
	/**
	 * @parameter expression="${project.build.finalName}"
	 * @required
	 * @readonly
	 */
	private String finalName;
	
	private final String REPLACE_END_PATTERNSTR = "^<!--\\s*replace\\s+end\\s*-->";
	
	private final String REPLACE_BEGIN_PATTERNSTR = "^<\\!--\\s*replace\\s+.*-->";
	
	private Pattern beginPattern = Pattern.compile(REPLACE_BEGIN_PATTERNSTR);
	private Pattern endPattern = Pattern.compile(REPLACE_END_PATTERNSTR);
	
	private final String REPPLACE_TYPE_STYLESHEET = "style";
	private final String REPPLACE_TYPE_SCRIPT = "script";
	
	/**
	 * maven插件调用的入口方法
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		
		for (String fileStr : includes) {
			try {
				File file = new File(basedir.getPath() + File.separator + webAppDir,fileStr);
				getLog().info("di-repjsp-plugin process file:" + file.getPath());
				
				isr = new InputStreamReader(new FileInputStream(file), encoding);
				br = new BufferedReader(isr);
				
				File outputbaseDir = new File(builddir.getPath()+File.separator+finalName);
				File outputDir = new File(outputbaseDir,new File(fileStr).getParent());
				
				File destFile = new File(outputDir, file.getName().replace("_dev", ""));
				
				if (destFile.exists()) {
					destFile.delete();
				}
				File destParent = destFile.getParentFile();
				if (!destParent.exists()) {
					destParent.mkdirs();
				}
				
				osw = new OutputStreamWriter(new FileOutputStream(destFile,true),encoding);
				bw = new BufferedWriter(osw);
				
				ReplaceRule rule = null;
				String line = null;
				while ((line = br.readLine()) != null) {
					if (rule == null) { //还没有匹配到开始标签
						Matcher matcher = beginPattern.matcher(line.trim());
						if (matcher.find()) {
							rule = createRule(line.trim());
							line = createResultString(rule);
						}
						writeLine(line,bw);
						continue;
					} 
					
					 //已经匹配了开始标签
					Matcher endMatcher = endPattern.matcher(line.trim());
					if (endMatcher.find()) {// 结束标签
						rule = null;
						continue;
					}
				}
			} catch (Exception e) {
				getLog().error(e);
			} finally {
				try {
					if (br != null) br.close(); 
					if (isr != null) isr.close(); 
					if (bw != null) bw.close(); 
					if (osw != null) osw.close(); 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private ReplaceRule createRule(String ruleStr) throws Exception {
		String typePatternStr = "type=\\s*\"(\\w+)\"";
		Pattern typePattern = Pattern.compile(typePatternStr);
		
		String type = null;
		Matcher typeMatcher = typePattern.matcher(ruleStr);
		if (typeMatcher.find()) {
			type = typeMatcher.group(1);
		}
		if (type == null || type.trim().equals("")) {
			throw new Exception("replace rule error, type not found.");
		}
		
		String valuePatternStr = null;
		String value = null;
		if (type.equalsIgnoreCase(REPPLACE_TYPE_STYLESHEET)) {
			valuePatternStr = "href=\\s*\"([\\S]+)\"";
		} else if (type.equalsIgnoreCase(REPPLACE_TYPE_SCRIPT)) {
			valuePatternStr = "src=\\s*\"([\\S]+)\"";
		}
		
		Pattern valuePattern = Pattern.compile(valuePatternStr);
		Matcher valueMatcher = valuePattern.matcher(ruleStr);
		if (valueMatcher.find()) {
			value = valueMatcher.group(0);
		}
		return new ReplaceRule(value, type);
		
	}
	
	private String createResultString(ReplaceRule rule) {
		if (REPPLACE_TYPE_STYLESHEET.equalsIgnoreCase(rule.getType())) {
			return "\t\t<link " + rule.getValue() + " rel=\"stylesheet\" type=\"text/css\" />";//styleTemplate;
		} else {
			return "\t\t<script type=\"text/javascript\" " + rule.getValue() + "></script>";//scriptTemplate;
		}
	}
	
	private void writeLine(String line,BufferedWriter bw) throws IOException {
		bw.write(line);
		bw.newLine();
	}
	
//	public static void main(String[] args) throws MojoExecutionException, MojoFailureException {
//		DIRepjspPlugin rep = new DIRepjspPlugin();
//		List<String> includes = new ArrayList<String>();
//		String fileStr = "E:/data/tmp/a_dev.jsp";
////		includes.add(fileStr);
//		
//		fileStr = "E:/data/tmp/b_dev.jsp";
//		includes.add(fileStr);
//		
//		rep.includes = includes;
//		
//		rep.execute();
//	}
}
