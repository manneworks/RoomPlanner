databaseChangeLog = {

	changeSet(author: "evv (generated)", id: "1393673615215-1") {
		createTable(tableName: "partner") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "partnerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "evv (generated)", id: "1393673615215-2") {
		createTable(tableName: "planner_request") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "planner_requePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "license_key", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "request_duration", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "TIMESTAMP", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "evv (generated)", id: "1393673615215-3") {
		createTable(tableName: "setting") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "settingPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "KEY", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "VALUE", type: "varchar(255)")
		}
	}

	changeSet(author: "evv (generated)", id: "1393673615215-4") {
		createTable(tableName: "system_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "system_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "PASSWORD", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "realm_code", type: "bigint")

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "evv (generated)", id: "1393673615215-5") {
		createIndex(indexName: "username_uniq_1393673615158", tableName: "partner", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "evv (generated)", id: "1393673615215-6") {
		createIndex(indexName: "KEY_uniq_1393673615173", tableName: "setting", unique: "true") {
			column(name: "KEY")
		}
	}

	changeSet(author: "evv (generated)", id: "1393673615215-7") {
		createIndex(indexName: "username_uniq_1393673615176", tableName: "system_user", unique: "true") {
			column(name: "username")
		}
	}
}
