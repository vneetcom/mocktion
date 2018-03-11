# mocktion
Limited Auction System made by Smart Contract on Ethereum.

# smart contract

pragma solidity ^0.4.8;
contract AuctionHouse {

	string public version="0.0.1";
	uint public startTime;
	address public owner;

	uint256 public totalSupply;
	uint256 public totalAmount;

	mapping (address => uint256) public balanceOf;

	struct Article {
		uint id;
		string name;
		uint startTime;
		uint basePrice;
		uint lastPrice;
		bool isReverse;
		address lastBidder;
		bool isValid;
	}
	mapping (address => Article) public display;
	mapping (uint => Article) public history;
	uint[] public hlist;

	event Transfer(address indexed from, address indexed to, uint256 value);
	event WithdrawalEther(address addr, uint amount, bool result);

	function AuctionHouse(
		uint256 _supply
	) public {
		startTime = now;
		owner = msg.sender;
		balanceOf[msg.sender] = _supply;
		totalSupply = _supply;
	}

	function () payable public {

		uint amount = msg.value;
		totalAmount += amount;

		require(balanceOf[owner] >= amount);
		require(balanceOf[msg.sender] + amount >= balanceOf[msg.sender]);

		balanceOf[owner] -= amount;
		balanceOf[msg.sender] += amount;

		emit Transfer(owner, msg.sender, amount);
	}

	function withdrawal(uint amount) public {
		require(balanceOf[msg.sender] >= amount);
		require(totalAmount >= amount);
		totalAmount -= amount;
		bool ok = msg.sender.call.value(amount)();
		emit WithdrawalEther(msg.sender, amount, ok);
	}

	function addToken(uint256 _value) public {
		require(owner == msg.sender);
		balanceOf[msg.sender] += _value;
		totalSupply += _value;
		emit Transfer(owner, owner, _value);
	}

	function exhibit(
			string	_name,
			uint	_basePrice,
			bool	_isReverse
	) public returns (uint) {

		uint _id = getSeconds();

		if(display[msg.sender].isValid == false){
			display[msg.sender] = Article({
				id : _id,
				name: _name,
				startTime: now,
				basePrice: _basePrice,
				lastPrice: _basePrice,
				isReverse: _isReverse,
				lastBidder: msg.sender,
				isValid: true
			});
		} else {
			_id = display[msg.sender].id;
		}

		return _id;
	}

	function bidPrice(address _address, uint _bidPrice) public returns (bool) {

		uint _lastPrice = display[_address].lastPrice;
		bool _isReverse = display[_address].isReverse;
		
		if(
			(_isReverse == true && _bidPrice < _lastPrice) ||
			(_isReverse == false && _bidPrice > _lastPrice)
		) {
			display[_address].lastPrice = _bidPrice;
			display[_address].lastBidder = msg.sender;
			return true;
		} else {
		    return false;
		}
	}

	function agree() public {
	    
		if(display[msg.sender].isValid == true) {
			uint _id = display[msg.sender].id;
			uint _lastPrice = display[msg.sender].lastPrice;
			bool _isReverse = display[msg.sender].isReverse;
			address _lastBidder = display[msg.sender].lastBidder;

    		if(_isReverse == false){
			require(balanceOf[_lastBidder] >= _lastPrice);
    			require(balanceOf[msg.sender] + _lastPrice >= balanceOf[msg.sender]);
    	
    			balanceOf[_lastBidder] -= _lastPrice;
    			balanceOf[msg.sender] += _lastPrice;
    
    			emit Transfer(msg.sender, _lastBidder, _lastPrice);
    		} else {
			require(balanceOf[msg.sender] >= _lastPrice);
    			require(balanceOf[_lastBidder] + _lastPrice >= balanceOf[_lastBidder]);
    
    			balanceOf[msg.sender] -= _lastPrice;
    			balanceOf[_lastBidder] += _lastPrice;
    
    			emit Transfer(_lastBidder, msg.sender, _lastPrice);
    		}

		}

		hlist.push(_id);

		history[_id] = Article({
		    id: display[msg.sender].id,
		    name: display[msg.sender].name,
		    startTime: display[msg.sender].startTime,
		    basePrice: display[msg.sender].basePrice,
		    lastPrice: display[msg.sender].lastPrice,
		    isReverse: display[msg.sender].isReverse,
		    lastBidder: display[msg.sender].lastBidder,
		    isValid: true
		});
		
		display[msg.sender].id=0;
		display[msg.sender].name="";
		display[msg.sender].startTime=0;
		display[msg.sender].basePrice=0;
		display[msg.sender].lastPrice=0;
		display[msg.sender].isReverse=false;
		display[msg.sender].lastBidder=msg.sender;
		display[msg.sender].isValid=false;

	}				

	function cancel() public {
		display[msg.sender].id=0;
		display[msg.sender].name="";
		display[msg.sender].startTime=0;
		display[msg.sender].basePrice=0;
		display[msg.sender].lastPrice=0;
		display[msg.sender].isReverse=false;
		display[msg.sender].lastBidder=msg.sender;
		display[msg.sender].isValid=false;
	}				

	function getBalance() public constant returns(uint) {
		return balanceOf[msg.sender];
	}

	function getArticle(address _address) public constant returns (uint, string, uint, uint, bool, address)
	{
		if(display[_address].isValid == true){
			return(
				display[_address].id,
				display[_address].name,
				display[_address].basePrice,
				display[_address].lastPrice,	
				display[_address].isReverse,
				display[_address].lastBidder
			);
		} else {
			(0,"",0,0,false,msg.sender);
		}
	}

	function getHlist() public constant returns (uint[])
	{
		return hlist;
	}

	function getHistory(uint _id) public constant returns (uint, string, uint, uint, bool, address)
	{
		if(history[_id].isValid == true){
			return(
				history[_id].id,
				history[_id].name,
				history[_id].basePrice,
				history[_id].lastPrice,	
				history[_id].isReverse,
				history[_id].lastBidder
			);
		} else {
			(0,"",0,0,false,msg.sender);
		}
	}

	function getVersion() public constant returns(string) {
		return version;
	}

	function getSeconds() public constant returns (uint) {
		if (startTime == 0) return 0;
		return (now - startTime);
	}

	function close() public {
		require(owner == msg.sender);
		selfdestruct(owner);
	}
}
