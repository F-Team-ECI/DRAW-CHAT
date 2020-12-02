package edu.eci.arsw.application.cache.redis.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.application.entities.util.Line;
import edu.eci.arsw.application.exceptions.AppException;
import edu.eci.arsw.application.cache.redis.DrawDAO;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrawDAOimpl implements DrawDAO {

    private static Jedis jedis;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String args[]){
        boolean useSsl = true;
        String cacheHostname = "drawchat.redis.cache.windows.net";
        String cachekey = "0qh0ieY3CRs5veSe8pxMNe2q+5YO6hihz6Ulyh4mcZE=";

        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        JedisShardInfo shardInfo = new JedisShardInfo(cacheHostname, 6380, useSsl);
        shardInfo.setPassword(cachekey); /* Use your access key. */
        jedis = new Jedis(shardInfo);

        // Perform cache operations using the cache connection object...

        // Simple PING command
        System.out.println( "\nCache Command  : Ping" );
    }


    @Override
    public void deleteSession(int group) throws AppException {
        String key = groupConstruct(group);
        System.out.println(jedis.get(key));
        jedis.del(key);
        System.out.println(jedis.get(key));
    }

    @Override
    public void createSession(int group) throws AppException {
        String key = groupConstruct(group);
        jedis.del(key);
    }

    @Override
    public void save(int group, Line line) throws AppException {
        String key = groupConstruct(group);

        try {
            System.out.println(mapper.writeValueAsString(line));
            jedis.lpush(key, mapper.writeValueAsString(line));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Line> getLines(int group) throws AppException {
        String key = groupConstruct(group);
        List<String> list = jedis.lrange(key, 0, Long.MAX_VALUE);
        List<Line> lines = new ArrayList<Line>();
        for(String s: list) {
            System.out.println("Stored string in redis:: "+s);
            try {
                lines.add(mapper.readValue(s, Line.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    private String groupConstruct(int id){
        return "groupPaint"+ id;
    }
}
