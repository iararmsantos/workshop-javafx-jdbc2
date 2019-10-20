package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }
    
    public void saveOrUpdate(Department obj){
        if(obj.getId() == null){
            //insert banco de dados
            dao.insert(obj);
        }else{
            //update banco de dados
            dao.update(obj);
        }
    }
    public void remove(Department obj){
        dao.deleteById(obj.getId());
    }
}
