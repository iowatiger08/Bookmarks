package liquibase.ext;

import liquibase.database.Database;
import liquibase.database.core.H2Database;
import liquibase.sqlgenerator.core.SetTableRemarksGenerator;
import liquibase.statement.core.SetTableRemarksStatement;

public class SetTableRemarksGeneratorH2 extends SetTableRemarksGenerator {

	public boolean supports(SetTableRemarksStatement statement, Database database) {

		boolean supports =  super.supports(statement, database) ||
			database instanceof H2Database;
		return supports;
	}
}
