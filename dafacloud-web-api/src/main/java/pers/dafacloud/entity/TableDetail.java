package pers.dafacloud.entity;

public class TableDetail {

    private int id;
    private String tableNameEn;
    private String tableDescription;
    private String tableColumn;
    private int project;
    private String moduleServer;
    private String tableOwner;
    private String groupsTable;

    private int pageNum;
    private int pageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableDescription() {
        return tableDescription;
    }

    public void setTableDescription(String tableDescription) {
        this.tableDescription = tableDescription;
    }

    public String getTableColumn() {
        return tableColumn;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getModuleServer() {
        return moduleServer;
    }

    public void setModuleServer(String moduleServer) {
        this.moduleServer = moduleServer;
    }

    public String getTableOwner() {
        return tableOwner;
    }

    public void setTableOwner(String tableOwner) {
        this.tableOwner = tableOwner;
    }

    public String getGroupsTable() {
        return groupsTable;
    }

    public void setGroupsTable(String groupsTable) {
        this.groupsTable = groupsTable;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
