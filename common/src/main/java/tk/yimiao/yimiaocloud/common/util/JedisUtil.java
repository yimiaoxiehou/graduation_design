/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-08 18:28
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class JedisUtil implements InitializingBean {

    @Value("127.0.0.1")
    String host;
    @Value("6379")
    int port;
    private JedisPool pool;
    @Value("Redis log : ")
    private String log_info;

//    public static void print(int index, Object obj) {
//        System.out.println(String.format("%d, %s", index, obj.toString()));
//    }
//
//    public static void main(String[] argv) {
//        Jedis jedis = new Jedis("redis://localhost", 32774);
//        jedis.flushDB();
//
//        // get set
//        jedis.set("hello", "world");
//        print(1, jedis.get("hello"));
//        jedis.rename("hello", "newhello");
//        print(1, jedis.get("newhello"));
//        jedis.setex("hello2", 1800, "world");
//
//        //
//        jedis.set("pv", "100");
//        jedis.incr("pv");
//        jedis.incrBy("pv", 5);
//        print(2, jedis.get("pv"));
//        jedis.decrBy("pv", 2);
//        print(2, jedis.get("pv"));
//
//        print(3, jedis.keys("*"));
//
//        String listName = "list";
//        jedis.del(listName);
//        for (int i = 0; i < 10; ++i) {
//            jedis.lpush(listName, "a" + i);
//        }
//        print(4, jedis.lrange(listName, 0, 12));
//        print(4, jedis.lrange(listName, 0, 3));
//        print(5, jedis.llen(listName));
//        print(6, jedis.lpop(listName));
//        print(7, jedis.llen(listName));
//        print(8, jedis.lrange(listName, 2, 6));
//        print(9, jedis.lindex(listName, 3));
//        print(11, jedis.lrange(listName, 0, 12));
//
//        // hash
//        String userKey = "userxx";
//        jedis.hset(userKey, "name", "jim");
//        jedis.hset(userKey, "age", "12");
//        jedis.hset(userKey, "phone", "18618181818");
//        print(12, jedis.hget(userKey, "name"));
//        print(13, jedis.hgetAll(userKey));
//        jedis.hdel(userKey, "phone");
//        print(14, jedis.hgetAll(userKey));
//        print(15, jedis.hexists(userKey, "email"));
//        print(16, jedis.hexists(userKey, "age"));
//        print(17, jedis.hkeys(userKey));
//        print(18, jedis.hvals(userKey));
//        jedis.hsetnx(userKey, "school", "zju");
//        jedis.hsetnx(userKey, "name", "yxy");
//        print(19, jedis.hgetAll(userKey));
//
//        // set
//        String likeKey1 = "commentLike1";
//        String likeKey2 = "commentLike2";
//        for (int i = 0; i < 10; ++i) {
//            jedis.sadd(likeKey1, String.valueOf(i));
//            jedis.sadd(likeKey2, String.valueOf(i * i));
//        }
//        print(20, jedis.smembers(likeKey1));
//        print(21, jedis.smembers(likeKey2));
//        print(22, jedis.sunion(likeKey1, likeKey2));
//        print(23, jedis.sdiff(likeKey1, likeKey2));
//        print(24, jedis.sinter(likeKey1, likeKey2));
//        print(25, jedis.sismember(likeKey1, "12"));
//        print(26, jedis.sismember(likeKey2, "16"));
//        jedis.srem(likeKey1, "5");
//        print(27, jedis.smembers(likeKey1));
//        jedis.smove(likeKey2, likeKey1, "25");
//        print(28, jedis.smembers(likeKey1));
//        print(29, jedis.scard(likeKey1));
//
//        String rankKey = "rankKey";
//        jedis.zadd(rankKey, 15, "jim");
//        jedis.zadd(rankKey, 60, "Ben");
//        jedis.zadd(rankKey, 90, "Lee");
//        jedis.zadd(rankKey, 75, "Lucy");
//        jedis.zadd(rankKey, 80, "Mei");
//        print(30, jedis.zcard(rankKey));
//        print(31, jedis.zcount(rankKey, 61, 100));
//        print(32, jedis.zscore(rankKey, "Lucy"));
//        jedis.zincrby(rankKey, 2, "Lucy");
//        print(33, jedis.zscore(rankKey, "Lucy"));
//        jedis.zincrby(rankKey, 2, "Luc");
//        print(34, jedis.zscore(rankKey, "Luc"));
//        print(35, jedis.zrange(rankKey, 0, 100));
//        print(36, jedis.zrange(rankKey, 0, 10));
//        print(36, jedis.zrange(rankKey, 1, 3));
//        print(36, jedis.zrevrange(rankKey, 1, 3));
//        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")) {
//            print(37, tuple.getElement() + ":" + tuple.getScore());
//        }
//
//        print(38, jedis.zrank(rankKey, "Ben"));
//        print(39, jedis.zrevrank(rankKey, "Ben"));
//
//        String setKey = "zset";
//        jedis.zadd(setKey, 1, "a");
//        jedis.zadd(setKey, 1, "b");
//        jedis.zadd(setKey, 1, "c");
//        jedis.zadd(setKey, 1, "d");
//        jedis.zadd(setKey, 1, "e");
//
//        print(40, jedis.zlexcount(setKey, "-", "+"));
//        print(41, jedis.zlexcount(setKey, "(b", "[d"));
//        print(42, jedis.zlexcount(setKey, "[b", "[d"));
//        jedis.zrem(setKey, "b");
//        print(43, jedis.zrange(setKey, 0, 10));
//        jedis.zremrangeByLex(setKey, "(c", "+");
//        print(44, jedis.zrange(setKey, 0, 2));
//
//        /*
//        JedisPool pool = new JedisPool();
//        for (int i = 0; i < 100; ++i) {
//            Jedis j = pool.getResource();
//            print(45, j.get("pv"));
//            j.close();
//        }*/
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool(host, port);
    }

    public void del(String key) {
        log.info(log_info + String.format("del {%s}", key));
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
    }

    public String get(String key) {
        log.info(log_info + String.format("get {%s}", key));
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return "";
    }

    public void set(String key, String value) {
        log.info(log_info + String.format("set {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
    }

    public long sadd(String key, String value) {
        log.info(log_info + String.format("sadd {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            return jedis.sadd(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public String spop(String key) {
        log.info(log_info + String.format("spop {%s}", key));
        try (Jedis jedis = pool.getResource()) {
            return jedis.spop(key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return "";
    }

    public long expire(String key, int seconds) {
        log.info(log_info + String.format("expire {%s} {%d}", key, seconds));
        try (Jedis jedis = pool.getResource()) {
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }


    public long srem(String key, String value) {
        log.info(log_info + String.format("srem {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            return jedis.srem(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public long scard(String key) {
        log.info(log_info + String.format("scard {%s}", key));
        try (Jedis jedis = pool.getResource()) {
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        log.info(log_info + String.format("sismember {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) {
        log.info(log_info + String.format("brpop {%d} {%s}", timeout, key));
        try (Jedis jedis = pool.getResource()) {
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    public List<String> hvals(String key) {
        log.info(log_info + String.format("hvals {%s}", key));
        try (Jedis jedis = pool.getResource()) {
            return jedis.hvals(key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    public String hget(String key, String field) {
        log.info(log_info + String.format("hget {%s} {%s}", key, field));
        try (Jedis jedis = pool.getResource()) {
            return jedis.hget(key, field);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return "";
    }

    public Boolean hexists(String key, String field) {
        log.info(log_info + String.format("hexists {%s} {%s}", key, field));
        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return false;
    }

    public void hset(String key, String field, String value) {
        log.info(log_info + String.format("hset {%s} {%s} {%s}", key, value, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
    }


    public void hdel(String key, String value) {
        log.info(log_info + String.format("hdel {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            jedis.hdel(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
    }

    public void hdel(String key, String[] values) {
        log.info(log_info + String.format("hdel {%s} ", key), values);
        try (Jedis jedis = pool.getResource()) {
            jedis.hdel(key, values);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
    }

    public long lpush(String key, String value) {
        log.info(log_info + String.format("lpush {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        log.info(log_info + String.format("lrange {%s} {%d} {%d}", key, start, end));
        try (Jedis jedis = pool.getResource()) {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    public long zadd(String key, double score, String value) {
        log.info(log_info + String.format("zadd {%s} {%f} {%s}", key, score, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public long zrem(String key, String value) {
        log.info(log_info + String.format("zrem {%s} {%s}", key, sub(value)));
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrem(key, value);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            log.error("发生异常", e);
            tx.discard();
        } finally {
            if (tx != null) {
                tx.close();
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }
    }

    public Set<String> zrange(String key, int start, int end) {
        log.info(log_info + String.format("zrange {%s} {%d} {%d}", key, start, end));
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        log.info(log_info + String.format("zrevrange {%s} {%d} {%d}", key, start, end));
        try (Jedis jedis = pool.getResource()) {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    public long zcard(String key) {
        log.info(log_info + String.format("zcard {%s}", key));
        try (Jedis jedis = pool.getResource()) {
            return jedis.zcard(key);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        log.info(log_info + String.format("zscore {%s} {%s}", key, member));
        try (Jedis jedis = pool.getResource()) {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            log.error("发生异常", e);
        }
        return null;
    }

    private String sub(String s) {
        if (s.length() > 50) {
            return s.substring(0, 50) + "...";
        } else
            return s;
    }
}
