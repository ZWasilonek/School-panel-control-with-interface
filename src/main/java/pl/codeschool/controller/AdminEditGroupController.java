package pl.codeschool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.codeschool.dao.GroupDao;
import pl.codeschool.mapper.DataFiller;
import pl.codeschool.model.Admin;
import pl.codeschool.model.Group;
import pl.codeschool.validation.BlankValidation;
import pl.codeschool.validation.CapacityValidation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/edit/group")
public class AdminEditGroupController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminEditGroupController.class);
    private static final String CHANGE_ADMIN_GROUP_NAME_ERROR = "you cannot change the ADMIN group name";
    private static final String NOT_UNIQUE_ERROR = "a group with this name already exists";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        String paramGroupName = request.getParameter("groupName");
        int groupId = (int) request.getSession().getAttribute("groupId");

        Map<String, String> fieldNames = Map.of("groupName", paramGroupName);
        boolean hasBlankFields = BlankValidation.hasBlankErrorsAttributes(fieldNames, request);
        boolean isUniqueGroupName = checkUniqueGroupName(paramGroupName, groupId, request);

        Map<String, Map<Integer, String>> capacitiesOfFields = Map.of("groupName", Map.of(256, paramGroupName));
        boolean hasCapacityExceededFields = CapacityValidation.hasCapacityErrorAttributes(capacitiesOfFields, request);

        if (!hasBlankFields && isUniqueGroupName && !hasCapacityExceededFields) {
            Group updatedGroup = new Group(groupId, paramGroupName);
            GroupDao.update(updatedGroup);

            request.setAttribute("isUpdated", true);
            request.getRequestDispatcher("/WEB-INF/admin-edit-group.jsp")
                    .forward(request, response);

        } else doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        String paramGroupName = request.getParameter("groupName");
        String paramGroupId = request.getParameter("groupId");

        Integer groupId = getGroupId(request, paramGroupId);

        if (groupId != null) {
            Group founded = GroupDao.read(groupId);

            if (founded == null) {
                request.setAttribute("groupNotExists", true);
            } else if (paramGroupName == null) {
                request.getSession().setAttribute("groupId", groupId);
                DataFiller.modelAttributesFiller(Map.of("groupName", founded.getName()), request);
            } else
                DataFiller.modelAttributesFiller(Map.of("groupName", paramGroupName), request);

            request.setAttribute("group", founded);
        }
        request.getRequestDispatcher("/WEB-INF/admin-edit-group.jsp")
                .forward(request, response);
    }

    private boolean checkUniqueGroupName(String groupName, int groupId, HttpServletRequest request) {
        if (groupName != null && !"".equals(groupName)) {
            Group foundedByName = GroupDao.readByName(groupName);
            Group foundedById = GroupDao.read(groupId);
            if (foundedByName != null && foundedByName.getId() != groupId) {
                request.setAttribute("notUniqueGroupName", NOT_UNIQUE_ERROR);
                return false;
            }
            if (foundedById != null && foundedById.getName().equals(Admin.getAdminUsername())) {
                request.setAttribute("tryingAdminGroupNameChange", CHANGE_ADMIN_GROUP_NAME_ERROR);
                return false;
            }
        }
        return true;
    }

    private Integer getGroupId(HttpServletRequest request, String paramGroupId) {
        Integer groupId = null;

        if (paramGroupId == null || "".equals(paramGroupId)) {
            groupId = (Integer) request.getSession().getAttribute("groupId");
        } else {
            try {
                groupId = Integer.parseInt(paramGroupId);
            } catch (NumberFormatException e) {
                LOGGER.info(e.getMessage());
            }
        }
        return groupId;
    }

}