package page.danya.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import page.danya.models.Absent;
import page.danya.models.AbsentWithStudent;
import page.danya.repository.AbsentRepository;

import java.util.ArrayList;
import java.util.List;

public class createAbsentDAO {

    private List<AbsentWithStudent> list;


    public void addObjectToList(AbsentWithStudent student){
        this.list.add(student);
    }

    public createAbsentDAO(int size) {
        list = new ArrayList<AbsentWithStudent>(size);
    }

    public createAbsentDAO(){
        list = new ArrayList<AbsentWithStudent>(6);
    }


    public List<AbsentWithStudent> getList() {
        return list;
    }

    public void setList(List<AbsentWithStudent> list) {
        this.list = list;
    }
}
