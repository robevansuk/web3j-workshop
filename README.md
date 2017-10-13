# Web3J workshop

As a part of JaxLondon's 4 day conference, there was a 1 day workshop on using Web3J to develop smart contracts.

Smart contracts are programs that are executed on the Ethereum blockchain.

# Instructions for use:

Prerequisites:
 
 install a geth client
 connect to the Ethereum network (Rinkeby, etc.)
 you will want to install the web3j command line tools
 you will need a wallet on the corresponding network
 Solidity should be installed locally as the commands will be compiled and executed as EVM bytecode
 
 
ABI files are application binary interfaces... these are the bindings for the various inputs/outputs/method declarations.
You will get a .bin binary file for anything you compile using Solidity.
 
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

The 2 smart contracts defined within the Greeter.sol definition file should be output as 2 .abi and .bin files each - so 4 files in total - in the build/ directory.

Next you'll want to test them on the Ethereum network - you first need to wrap them in a special Web3j wrapper which will parse the contract bytecode into a Java String which in tern will be pushed to the network.

```web3j solidity generate build/greeter.bin build/greeter.abi -p org.web3j.example.generated -o src/main/java/```

