MyRepo
======

My Solution
-----------

Please review my queries before I complete final solution.

In the given codebase, orchestrator is passed to processor class and orchestrator already has a reference to set of processor classes. 

This causes a cyclic dependency as AbstractProcessor class invokes receive operation on Orchestrator. 

Also, the number of child elements expected in test case is 5, however since there are only 3 processor classes are present - atmost only 3 objects can be returned.

Please advise if there is a gap in my understanding here.
