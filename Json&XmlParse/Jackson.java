/* 基本使用 */
ObjectMapper mapper = new ObjectMapper(); 
Person person = new Person(); 
person.setName("Tom"); 
person.setAge(40);
// 序列化
String res = mapper.writeValueAsString(person);
// 输出格式化后的字符串(有性能损耗) 
String jsonString = mapper.writerWithDefaultPrettyPrinter() 
    .writeValueAsString(person);
// 反序列化
Person deserializedPerson = mapper.readValue(jsonString, Person.class);

/* 对于集合、泛型 */
// List
CollectionType javaType = mapper.getTypeFactory() 
    .constructCollectionType(List.class, Person.class); 
List<Person> personList = mapper.readValue(jsonInString, javaType);
// 第二种方式
List<Person> personList = mapper.readValue(jsonInString, new TypeReference<List<Person>>(){});

// Map
//第二参数是 map 的 key 的类型，第三参数是 map 的 value 的类型 
MapType javaType = mapper.getTypeFactory()
    .constructMapType(HashMap.class, String.class, Person.class); 
Map<String, Person> personMap = mapper.readValue(jsonInString, javaType);
// 第二种方式
Map<String, Person> personMap = mapper.readValue(jsonInString, new TypeReference<Map<String, Person>>() {});