
import org.drools.planner.config.SolverFactory
import org.drools.planner.config.XmlSolverFactory
import org.drools.planner.core.Solver
import org.drools.planner.core.score.director.ScoreDirector

class BootStrap {

	def grailsApplication

    def init = { servletContext ->

    		log.debug("Build solver")
			SolverFactory solverFactory = new XmlSolverFactory()
			solverFactory.configure("/drools/roomScheduleSolverConfig.xml")
			Solver solver = solverFactory.buildSolver()
			ScoreDirector scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
			grailsApplication.config.solver = solver
			grailsApplication.config.scoreDirector = scoreDirector
			
			grailsApplication.config.startNanoTime = System.nanoTime()
    }


    def destroy = {
    }
}
