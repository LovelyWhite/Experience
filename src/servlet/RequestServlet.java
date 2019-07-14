package servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import model.Reply;
import org.apache.ibatis.session.SqlSession;
import pojo.Experience;
import util.Database;
import util.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/*
 *接口格式 type：string  data：json
 *
 */
@WebServlet(name = "RequestServlet")
public class RequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String type = request.getParameter("type");
        PrintWriter writer = response.getWriter();
        SqlSession sqlSession = Database.getSqlSession();
        String dTemp = request.getParameter("data");
        JSONObject data;
        if (!dTemp.equals(""))
            data = (JSONObject) JSON.parse(request.getParameter("data"));
        else
            data = new JSONObject();
        log("type:" + type);
        log("data:" + data.toJSONString());
        if ("verify".equals(type)) {
            String username = data.getString("username");
            String password = data.getString("password");
            String role = data.getString("role");
            log("reply:");
            if (username != null && password != null && role != null && !username.equals("") && !password.equals("") && !role.equals("")) {
                UUID uuid = UUID.randomUUID();
                List<pojo.User> users = sqlSession.selectList("UserMapper.selectUserByUserName", username);
                int size = users.size();
                if (size == 0) {
                    log("notExist");
                    writer.write(JSON.toJSONString(new Reply("notExist", "")));
                } else {
                    if (password.equals(users.get(0).getPassword()) && role.equals(users.get(0).getRole())) {
                        log("success");
                        User.userLogin(uuid, users.get(0));
                        writer.write(JSON.toJSONString(new Reply("success", uuid.toString())));
                    } else {
                        log("passwordError");
                        writer.write(JSON.toJSONString(new Reply("passwordError", "")));
                    }
                }

            } else {
                log("failed");
                writer.write(JSON.toJSONString(new Reply("failed", "")));
            }
        } else if ("experience".equals(type)) {
            log("reply:");
            String filter = data.getString("filter");
            if (filter != null) {
                List<Experience> experiences = sqlSession.selectList("ExperienceMapper.selectAllExperience", filter);
                if (experiences.size() == 0) {
                    log("null");
                    writer.write(JSON.toJSONString(new Reply("null", "")));
                } else {
                    log("success");
                    writer.write(JSON.toJSONString(new Reply("success", JSON.toJSONString(experiences, SerializerFeature.DisableCircularReferenceDetect))));
                }

            } else {
                log("failed");
                writer.write(JSON.toJSONString(new Reply("failed", "")));
            }
        } else if ("login".equals(type)) {
            log("reply:");
            String uuid = data.getString("uuid");
            if (uuid != null && !"".equals(uuid)) {
                if (User.isLogin(UUID.fromString(uuid))) {
                    log("uuid:" + uuid);
                    writer.write(JSON.toJSONString(new Reply("success", JSON.toJSONString(User.getUserInfo(UUID.fromString(uuid))))));
                } else {
                    log("notOnline");
                    writer.write(JSON.toJSONString(new Reply("notOnline", "")));
                }

            } else {
                log("failed");
                writer.write(JSON.toJSONString(new Reply("failed", "")));
            }
        } else if ("logout".equals(type)) {
            log("reply:");
            String uuid = data.getString("uuid");
            if (uuid != null && !"".equals(uuid)) {
                if (User.isLogin(UUID.fromString(uuid))) {
                    log("uuid:" + uuid);
                    User.userExit(UUID.fromString(uuid));
                    writer.write(JSON.toJSONString(new Reply("success", "")));
                } else {
                    log("notOnline");
                    writer.write(JSON.toJSONString(new Reply("notOnline", "")));
                }
            } else {
                log("failed");
                writer.write(JSON.toJSONString(new Reply("failed", "")));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
