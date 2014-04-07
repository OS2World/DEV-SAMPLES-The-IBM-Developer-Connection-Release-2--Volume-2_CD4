Global -- will be exteded by Object servers--provides factory, locking, commit/rollback facilities, security
Factory -- 1 instance per Object server--creates persistent entities--guarantees uniqueness on object server
PersistentEntity -- RMI interface for PE's
PersistentEntityImpl -- default information stored here (handle, location handle, accessMode)--also provides 
		add, update, delete transaction methods--uses PEER objects to access database

Peer.......use JDBC book to explain


Customer -- RMI interface
CustomerImpl -- implementation
CustomerFactory -- uncouples creation of customer from ....blah, blah, blah--use Design Patterns to explain.


Containers will be notifiers and GUI screens will be observers