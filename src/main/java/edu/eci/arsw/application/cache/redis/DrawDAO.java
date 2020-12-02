package edu.eci.arsw.application.cache.redis;

import edu.eci.arsw.application.entities.util.Line;
import edu.eci.arsw.application.exceptions.AppException;

import java.util.List;

public interface DrawDAO {

    public void deleteSession(int group) throws AppException;

    public void createSession(int group) throws AppException;

    public void save(int group, Line line) throws AppException;

    public List<Line> getLines(int group) throws  AppException;

}
