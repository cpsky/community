package cpsky.community.cache;

import cpsky.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * \* Author: sky
 * \* Date: 2019/6/14
 * \* Description:
 * \
 */
public class TagCache {
    public static List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js", "php", "css", "html", "java", "python", "c#"
                , "node.js", "golang", "object-c", "rust", "perl"));

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring", "yii", "django"));

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "apache", "centos", "负载均衡", "unix", "hadoop", "tomcat"));

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "oracle", "nosql", "sqlserver", "postgresql", "sqlite"));

        TagDto tool = new TagDto();
        tool.setCategoryName("工具");
        tool.setTags(Arrays.asList("git", "github", "vim", "vscode", "eclipse", "maven", "ide", "idea", "svn", "visual studio"));

        tagDtos.add(program);
        tagDtos.add(db);
        tagDtos.add(server);
        tagDtos.add(framework);
        tagDtos.add(tool);

        return tagDtos;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.splitByWholeSeparatorPreserveAllTokens(tags, ",");
        //String[] split = tags.split(",", -1);
        List<TagDto> tagDtos = get();

        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> (!tagList.contains(t))).collect(Collectors.joining(", "));
        for (String s : split) {
            if ("".equals(s)){
                invalid += "有空值";
            }
        }
        return invalid;
    }
}