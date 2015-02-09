# levenberg-marquardt
A lightweight Java implementation of Levenberg-Marquardt algorithm

Augmented normal equation
============================

      (H + uI) * h = -g

where:
* H is the Hessian matrix of the chi-squared error function
* g is the gradient (Jacobian) vector of the chi-squared error function
* u is the damping value

Adjusting damping value
============================
Damping value is adjusted at each iteration. The adjustment follows the algorithm presented in ***Methods for non-linear least squares problems*** by *Kaj Madsen*, *Hans Bruun Nielsen*, *Ole Tingleff*. The lecture note can be downloaded [here](http://www.imm.dtu.dk/pubdb/views/edoc_download.php/3215/pdf/imm3215.pdf) 
