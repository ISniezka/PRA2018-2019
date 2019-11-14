package hibernate.saveAndRead;

import hibernate.model.*;

import java.util.ArrayList;

public class Temporary {

    private ArrayList <Czlonek> MemberList;
    private ArrayList <Klub> ClubList;
    private ArrayList <Prezes> PresidentList;
    private ArrayList <Spotkanie> MeetingList;
    private ArrayList <Obecnosc> PresenceList;

    public Temporary() {
    }

    public Temporary(ArrayList<Czlonek> memberList) {
        MemberList = memberList;
    }

    public Temporary(ArrayList<Czlonek> memberList, ArrayList<Klub> clubList, ArrayList<Prezes> presidentList, ArrayList<Spotkanie> meetingList, ArrayList<Obecnosc> presenceList) {
        MemberList = memberList;
        ClubList = clubList;
        PresidentList = presidentList;
        MeetingList = meetingList;
        PresenceList = presenceList;
    }

    public ArrayList<Czlonek> getMemberList() {
        return MemberList;
    }

    public void setMemberList(ArrayList<Czlonek> memberList) {
        MemberList = memberList;
    }

    public ArrayList<Klub> getClubList() {
        return ClubList;
    }

    public void setClubList(ArrayList<Klub> clubList) {
        ClubList = clubList;
    }

    public ArrayList<Prezes> getPresidentList() {
        return PresidentList;
    }

    public void setPresidentList(ArrayList<Prezes> presidentList) {
        PresidentList = presidentList;
    }

    public ArrayList<Spotkanie> getMeetingList() {
        return MeetingList;
    }

    public void setMeetingList(ArrayList<Spotkanie> meetingList) {
        MeetingList = meetingList;
    }

    public ArrayList<Obecnosc> getPresenceList() {
        return PresenceList;
    }

    public void setPresenceList(ArrayList<Obecnosc> presenceList) {
        PresenceList = presenceList;
    }
}
