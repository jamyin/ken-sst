package com.tianfang.common.zookeeper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper共享锁实现类，此对象为线程不安全，应实例化后，以线程封闭使用
 * @author rkzhang
 */
public class ZookeeperLock {
	
	private ZooKeeper zk;
	
	private String root;
	
	private String ownNode;
	
	private String lockPath;
	
	public ZookeeperLock(String root) throws KeeperException, InterruptedException {
		super();
		this.zk = ZookeeperFactory.achieveZkClient();
		this.root = root;
		Stat stat = zk.exists(root, false); 
		if(stat == null) {
			zk.create(root, "1".getBytes(), Ids.OPEN_ACL_UNSAFE,
					   CreateMode.PERSISTENT);
		}
	}

	/**
	 * 获取锁
	 * @param code
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void achieveLock(String code) throws KeeperException, InterruptedException{ 
		lockPath = getLockPath(code);
		Stat stat = zk.exists(getLockPath(code), false); 
		if(stat == null){
			zk.create(lockPath, "1".getBytes(), Ids.OPEN_ACL_UNSAFE,
					   CreateMode.PERSISTENT);
		}
		
		String znode = zk.create(getDataPath(code), "1".getBytes(), Ids.OPEN_ACL_UNSAFE,
				   CreateMode.EPHEMERAL_SEQUENTIAL);
		this.ownNode = znode;
		System.out.println("own znode : " + znode);
		getLock();
    }
	
	/**
	 * 释放锁
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public void releaseLock() throws InterruptedException, KeeperException { 
		zk.delete(ownNode, -1);
		System.out.println("delete znode : " + ownNode);
		List<String> list = zk.getChildren(lockPath, false); 
		if(list == null || list.isEmpty()) {
			zk.delete(lockPath, -1);
			System.out.println("delete znode : " + lockPath);
		}
	}

	private void getLock() throws KeeperException, InterruptedException {
		List<String> list = zk.getChildren(lockPath, false); 
		String lastNode = lockPath + "/" + getLastNode(list);
		System.out.println("last znode : " + lastNode);
		if(isLastNode(ownNode, lastNode)) { 
			return;
		} else {
			waitForLock(lastNode);
		}
	}
	
    private void waitForLock(String lowerPath) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(lowerPath, false); 
        while(stat != null){ 
            Thread.sleep(200);
            stat = zk.exists(lowerPath, false); 
        } 
        getLock();
    }

	private boolean isLastNode(String ownNode, String lastNode) {
        return ownNode.equals(lastNode) ? true : false;
	}

	private String getLastNode(List<String> list) {
		String[] nodes = list.toArray(new String[list.size()]); 
		for(String node : nodes) {
			 System.out.println("child node - " + node);
		}
		
        Arrays.sort(nodes); 
        String lastNode = nodes[0];
		return lastNode;
	}

	private  String getDataPath(String code) {
		return root + "/" + code + "/" + code;
	} 
	
	public String getLockPath(String code) {
		return root + "/" + code;
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		// /sport/orders 目录是代表需要同步的数据的业务类型,需要管理员在zookeeper上先建立好
		ZookeeperLock lock  = new ZookeeperLock("/sport/orders");
		// lock的目录是指具体发生锁的数据, 比如下面我的意思是对预约某一场馆在2015-03-31这个数据上加锁,具体业务需要加锁的数据值, 商品编号等等.
		lock.achieveLock("2015-03-31:1638dbbe-ccf7-41d1-8fb5-a3d3f75e87c5");
		System.out.println("do action");
		CountDownLatch cd = new CountDownLatch(1);
		cd.await();
		//释放锁,必须
		lock.releaseLock();

	}
}
