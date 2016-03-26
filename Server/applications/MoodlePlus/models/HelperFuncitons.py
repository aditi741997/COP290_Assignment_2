def GetIndivComplaints():
	if auth.is_logged_in():
		allcomp = db(db.indiv_complaints.username==auth.user.username).select()
		return allcomp
	return []

def isadmin():
	# return len(db(db.admin_info.username==auth.user.username).select())>0
	return True

def AllCategories():
	a = db(db.complaint_category.category_id>=0).select()
	b = map(lambda x: x["category_description"],a)
	c = db(db.users.username==auth.user.username).select()[0]
	host,insti,oth = c["hostel_pref"],c["insti_pref"],c["extra_pref"]
	arr = [host,insti,oth]
	s="0"*100
	arr= map(lambda x: x+s,arr)
	d= []
	for i in xrange(len(b)):
		d.append((b[i],arr[0][i],arr[1][i],arr[2][i]))
	return d

def isspecial():
	# return len(db(db.admin_info.username==auth.user.username and db.admin_info.admin_level==-1).select())>0
	return True