package crypto;

import crypto.Miners.LyingMiner;
import crypto.Miners.Miner;

public class App 
{
    public static void main(String[] args) {
        BlockChain blockchain = new BlockChain();
        int numberOfMiners = Runtime.getRuntime().availableProcessors(); // Optimal threads based on available processors

        System.out.println("Starting " + numberOfMiners + " miners.");
        for (int i = 0; i < numberOfMiners; i++) {
            Miner miner = new Miner(blockchain, "Miner " + i);
            
            miner.start();
            
        }
        try 
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
