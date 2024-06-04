package crypto;

import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

import crypto.Utilities.SHA256MerkleTree;

public class BlockChain 
{
    private List<Block> blocks = new ArrayList<>();
    public final static int difficulty = 5;
	private ReentrantLock lock = new ReentrantLock();

	public BlockChain()
	{
		addBlock(new Block("Hi im the first block", "0"), "StarterBlock");
	}

    public String getLastHash()
	{
        return blocks.size() > 0 ? blocks.get(blocks.size() - 1).hash : "0";
    }

    public boolean isChainValid()
	{
        StringBuilder sb = new StringBuilder();
        for (Block block : blocks) {
            sb.append(block.hash);
        }
        InputStream stream = new ByteArrayInputStream(sb.toString().getBytes());
        byte[] treeHash = SHA256MerkleTree.sha256MerkleTree(stream, 1024);
        if(treeHash == null)
		{
			return false;
		}
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		for(int i=1; i < blocks.size(); i++) {
			currentBlock = blocks.get(i);
			previousBlock = blocks.get(i-1);
			if(!currentBlock.hash.equals(currentBlock.calculateHash()))
			{
				System.out.println("Current Hashes not equal");			
				return false;
			}
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) 
			{
				System.out.println("Previous Hashes not equal");
				return false;
			}
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget))
			{
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
    }

	public int getDifficulty()
	{
		return difficulty;
	}

	public int getLastBlock()
	{
		return blocks.size();
	}

    public synchronized boolean addBlock(Block newBlock, String id)
	{
		if(!isValidNewBlock(newBlock))
		{
			return false;
		}
		lock.lock();
        try 
		{
			blocks.add(newBlock);
            return true;
        } 
		finally 
		{
            lock.unlock();
        }
    }

	private boolean isValidNewBlock(Block newBlock)
	{
		if(blocks.isEmpty())
		{
			return true;
		}
		Block current = blocks.get(blocks.size()-1);
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		if(!newBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
			System.out.println("This block hasn't been mined");
			return false;
		}
		return newBlock.previousHash.equals(current.hash);
	}
}
