使用通用mapper更新后

【【account】】
public interface AccountMapper extends Mapper<Account> {
    @Select("select sum(salary) from account where roleid is not null;")
    public Float findSumSalary();
}



【【【Subscribe】】】
@Column(name = "checkInTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkintime;

【【【Contract】】】
@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "checkInTime")
    private Date checkintime;


【【【Chatmapper】】】
public interface ChatMapper extends Mapper<Chat> {
    @Select("select chat.userId,chat.username,max(any_value(chat.createTime)) as lasttime from (\n" +
            "select chat.id,chat.userId,account.username,chat.createTime from chat join account on chat.userId = account.id\n" +
            ") as chat group by chat.userId;")
    public List<FriendsDto> findFriends();
}
