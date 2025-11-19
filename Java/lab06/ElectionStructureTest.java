import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("election.Election")
       .that(hasUsualModifiers());
}

@Test
public void fieldVoteCounts() {
    it.hasField("voteCounts: array of int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodAddVote() {
    it.hasMethod("addVote", withParams("candidate: election.candidate.Candidate"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodAddVotes() {
    it.hasMethod("addVotes", withParams("candidate: election.candidate.Candidate", "count: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing();
}

@Test
public void methodGetWinner() {
    it.hasMethod("getWinner", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("election.candidate.Candidate");
}

@Test
public void methodGetCandidatesWithMoreVotesThan() {
    it.hasMethod("getCandidatesWithMoreVotesThan", withParams("voteCount: int"))
      .that(hasUsualModifiers())
      .thatReturns("array of election.candidate.Candidate");
}

@Test
public void methodGetCandidateCountWithMoreVotesThan() {
    it.hasMethod("getCandidateCountWithMoreVotesThan", withParams("voteCount: int"))
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturns("int");
}

@Test
public void methodGetWinningIdx() {
    it.hasMethod("getWinningIdx", withNoParams())
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, MODIFIABLE, VISIBLE_TO_NONE)
      .thatReturns("int");
}

void main() {}


