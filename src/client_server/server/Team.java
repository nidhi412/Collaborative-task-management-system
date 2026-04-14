package client_server.server;



import models.Member;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Member> members;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(String memberName) {
        members.removeIf(member -> member.getName().equalsIgnoreCase(memberName));
    }
}
