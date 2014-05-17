databaseChangeLog = {

	changeSet(author: "vlad (generated)", id: "1400319501808-1") {
		createTable(tableName: "PARTNER") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_F")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ENABLED", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "USERNAME", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vlad (generated)", id: "1400319501808-2") {
		createTable(tableName: "PLANNER_REQUEST") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_B")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "LICENSE_KEY", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "REQUEST_DURATION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "TIMESTAMP", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vlad (generated)", id: "1400319501808-3") {
		createTable(tableName: "SETTING") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_A")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "KEY", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "VALUE", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "vlad (generated)", id: "1400319501808-4") {
		createTable(tableName: "SYSTEM_USER") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_2")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "REALM_CODE", type: "BIGINT")

			column(name: "USERNAME", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vlad (generated)", id: "1400319501808-5") {
		createIndex(indexName: "UK_PC69BEBKRF0AQIIYJRB2683M2_INDEX_F", tableName: "PARTNER", unique: "true") {
			column(name: "USERNAME")
		}
	}

	changeSet(author: "vlad (generated)", id: "1400319501808-6") {
		createIndex(indexName: "UK_JTFD2BH4AETVC6PALQ0PW2BNX_INDEX_A", tableName: "SETTING", unique: "true") {
			column(name: "KEY")
		}
	}

	changeSet(author: "vlad (generated)", id: "1400319501808-7") {
		createIndex(indexName: "UK_74Y7XIQRVP39WYCN0RON4XQ4H_INDEX_2", tableName: "SYSTEM_USER", unique: "true") {
			column(name: "USERNAME")
		}
	}
}
