package liquibase.ext;

import liquibase.database.core.H2Database;
import liquibase.sqlgenerator.core.SetColumnRemarksGenerator;

public class SetColumnRemarksGeneratorH2 extends SetColumnRemarksGenerator {

	public boolean supports(liquibase.statement.core.SetColumnRemarksStatement statement, liquibase.database.Database database) {
		return super.supports(statement, database) ||
			database instanceof H2Database;
	}
}
