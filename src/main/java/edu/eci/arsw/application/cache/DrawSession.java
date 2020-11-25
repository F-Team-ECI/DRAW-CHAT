package edu.eci.arsw.application.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.application.entities.Group;
import edu.eci.arsw.application.entities.util.Line;
import edu.eci.arsw.application.persistence.DrawPersistenceService;

@Service
public class DrawSession {

    @Autowired
    private DrawPersistenceService drawPersistenceService;
    
    public Group group;
    public List<Line> drawings;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Line> getDrawings() {
        return drawings;
    }

    public void setDrawings(List<Line> drawings) {
        this.drawings = drawings;
    }
    
}
