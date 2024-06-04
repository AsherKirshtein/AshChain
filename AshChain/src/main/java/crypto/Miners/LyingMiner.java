package crypto.Miners;

import crypto.Block;
import crypto.BlockChain;

public class LyingMiner extends Thread{
    private final BlockChain blockchain;
    private int currentBlock;
    private final String ID;

    //This miner is going to try to trick our chain and give it false hashes
    public LyingMiner(BlockChain blockchain, String ID) 
    {
        this.blockchain = blockchain;
        this.currentBlock = blockchain.getLastBlock();
        this.ID = ID;
    }

    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + "(I'm LYING SHHHHHHHHHH) is starting to mine blocks.");
        while(!this.isInterrupted() && blockchain.isChainValid())
        {
            String lastHash = blockchain.getLastHash();
            currentBlock = blockchain.getLastBlock();
            System.out.println(Thread.currentThread().getName() + "(I'm LYING SHHHHHHHHHH) is trying to mine block " + currentBlock + "...");
            Block newBlock = new Block("(I'm LYING SHHHHHHHHHH)Hey I'm the " + currentBlock + " block", lastHash);
            if(blockchain.addBlock(newBlock, this.ID))
            {
                System.out.println(Thread.currentThread().getName() + "(I'm LYING SHHHHHHHHHH) just got block " + currentBlock);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(blockchain.isChainValid())
        {
            System.out.println(Thread.currentThread().getName() + "(I'm LYING SHHHHHHHHHH) says: Blockchain is valid.");
        } 
        else 
        {
            System.out.println(Thread.currentThread().getName() + "(I'm LYING SHHHHHHHHHH) says: Blockchain validation failed.");
        }
    }
}
