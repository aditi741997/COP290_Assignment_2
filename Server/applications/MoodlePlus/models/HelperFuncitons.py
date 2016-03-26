def indiv_complaints():
	if auth.is_logged_in():
		allcomp = db(db.indiv_complaints.username==auth.user.username).select()
		return allcomp
	return []