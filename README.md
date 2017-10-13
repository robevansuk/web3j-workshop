# Web3J workshop

As a part of JaxLondon's 4 day conference, there was a 1 day workshop on using Web3J to develop smart contracts.

Smart contracts are programs that are executed on the Ethereum blockchain.

# Instructions for use:

Prerequisites:
 
 - install a geth client
 - connect to the Ethereum network (Rinkeby, Kovan, testnet, etc.) - see geth command in the setup section
 - Web3j command line tools
 - you will need a wallet on the test network (at the moment a wallet on the test network works on all test networks not just the one you were connected to when it was created)
 - Solidity should be installed locally as the commands will be compiled and executed as EVM bytecode
 
ABI files are application binary interfaces... these are the bindings for the various inputs/outputs/method declarations.
You will get a .bin binary file for anything you compile using Solidity.

# Setup

You'll need to have the Solidity compiler installed for your system and executable from the command line.

Run geth:
```./geth --rpcapi personal,db,eth,net,web3 --rpc --rinkeby```

You'll need a wallet address. Using the web3j command line tools you can create a wallet with:

```web3j wallet create``` 

Follow the instructions and you'll end up with a wallet file on your machine which contains the wallet address and private keys in encrypted format. Get the address from the file and update the `application.properties` file in this application with the address, the password to your wallet (used locally only) and the file path to your wallet file (a `.json` file)

Now you'll want some ether to run the next few steps - all computation on the Ethereum Virtual Machine needs to be paid for. To pay for it you use "Ether" - analagous to 'gas' for your car. You can't get very far without it.
Go to http://gist.github.com and set up a gist with your wallet address as the content... as per: https://gist.github.com/robevansuk/58a5da2e6a7641ad7add019dcac696a6
 
Go to https://faucet.rinkeby.io/ and paste in the gist URL. You can then select a set amount of Ether you want deposited to your digital wallet.

You should be ready to go now.

# Compiling using solidity compiler
 
Within `src/main/resources/solidity` you will find smart contracts.
Smart contracts, once on the blockchain, cannot be changed. They can be terminated or new versions deployed, but never deleted from the blockchain.

Within the `.../resources/solidity` directory you'll find `Greeter.sol` - this is a HelloWorld program for the ethereum blockchain. To compile it from the root of this project, execute:

```solc src/main/resources/solidity/Greeter.sol --bin --abi -- optimize -o build/```

 
This will output a binary file (.bin) and an ABI (application binary interface) file.
  
The Binary is the application in binary format. Web3j will wrap this before sending it out to the Blockchain using a wrapper within Web3j.
The ABI is the interface, specifying the methods, their inputs/outputs.
 
To compile the Greeter.sol smart contract, ready to deploy it to the Ethereum network, use:

```solc -o build/ --abi --bin --optimize src/main/resources/solidity/Greeter.sol```

The 2 smart contracts defined within the `Greeter.sol` definition file should be output as 2 `.abi` and `.bin` files each - so 4 files in total - in the `build/` directory.

Next you'll want to test them on the Ethereum test network, of which there are many. I've used the "Rinkeby" network (network id 4 - see `version.ClientVersion`)- you first need to wrap them in a special Web3j wrapper which will parse the contract bytecode into a Java String which, in turn, will be pushed/deployed to the network. This step takes a few minutes sometimes so be patient...

```web3j solidity generate build/greeter.bin build/greeter.abi -p org.web3j.example.generated -o src/main/java/```



