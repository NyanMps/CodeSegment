// 第一种方式，程序（手动）生成
public String addBook(Book book) {
    String sql = "insert into book(id,name,price,author,publishdate,categoryid,status) values (?,?,?,?,?,?,?)";
    String id = CommonsUtils.getUUIDTo32();
    try {
        qr.update(sql, id, book.getName(),
                book.getPrice(), book.getAuthor(), book.getPublishdate(),
                book.getCategoryid(), book.getStatus());
        return id;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

// ...
public static String getUUIDTo32() {
    return UUID.randomUUID().toString().replace("-", "");
}


// 第二种方式，数据库生成
public String addCategory(Category category) {
    // 如果不需要返回生成的 UUID 可以直接这样写
    // String sql = "insert into book values (replace(uuid(),'-',''),?,?,?,?,?,?)";
    String sql = "insert into category(id,name) values (?,?)";
    String getIdSql = "select replace(uuid(),'-','') id";
    try {
        Map<String, Object> map = qr.query(getIdSql, new MapHandler());
        category.setId(map.get("id").toString());
        qr.update(sql, category.getId(), category.getName());
        return category.getId();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


// Oracle 的 UUID 生成方式： select sys_guid() from dual;
// 自增的话除了序列的 nextval 还可以 max(id) + 1