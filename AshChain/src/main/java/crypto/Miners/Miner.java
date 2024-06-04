package crypto.Miners;

import java.util.List;
import java.util.ArrayList;


import crypto.Block;
import crypto.BlockChain;

public class Miner extends Thread
{
    private final BlockChain blockchain;
    private int currentBlock;
    private final String ID;
    private List<Block> blocksMined;

    public Miner(BlockChain blockchain, String ID) 
    {
        this.blockchain = blockchain;
        this.currentBlock = blockchain.getLastBlock();
        this.ID = ID;
        this.blocksMined = new ArrayList<>();
    }

    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + " is starting to mine blocks.");
        while(!this.isInterrupted() && blockchain.isChainValid())
        {
            String lastHash = blockchain.getLastHash();
            currentBlock = blockchain.getLastBlock();
            System.out.println(Thread.currentThread().getName() + " is trying to mine block " + currentBlock + "...");
            Block newBlock = new Block("Hey I'm the " + currentBlock + " block", lastHash);
            int difficulty = this.blockchain.getDifficulty();
            newBlock.mineBlock(difficulty, this.ID);
            if(blockchain.addBlock(newBlock, this.ID))
            {
                this.blocksMined.add(newBlock);
                System.out.println(Thread.currentThread().getName() + " just got block " + currentBlock);
            }
        }

        if(blockchain.isChainValid())
        {
            System.out.println(Thread.currentThread().getName() + " says: Blockchain is valid.");
        } 
        else 
        {
            System.out.println(Thread.currentThread().getName() + " says: Blockchain validation failed.");
        }
    }

    @Override
    public String toString()
    {
        return this.ID + ": \nBlocks Mined: " + this.blocksMined.size();
    }

}
