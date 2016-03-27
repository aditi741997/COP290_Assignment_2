def GetIndivComplaints():
	if auth.is_logged_in():
		allcomp = db(db.indiv_complaints.username==auth.user.username).select()
		return allcomp
	return []

def GetHostelComplaints():
	# TODO
	return []

def GetInstiComplaints():
	# TODO
	return []

def isadmin():
	# TODO: remove comment
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
	# TODO: Remove comment
	# return len(db(db.admin_info.username==auth.user.username and db.admin_info.admin_level==-1).select())>0
	return True

def GetCategory(x):
	# Takes in complaint as input and returns category in string
	catid = x["complaint_type"]
	value = db(db.complaint_category.category_id==catid).select()
	# value+=[{catid:"Unknown"}]
	# return value[catid]
	if value==[]:
		return "Unknown"
	else:
		return value[0]["category_description"]

def GetHostel(x):
		# Takes in complaint as input and returns hostel in string
	catid = x["hostel_id"]
	value = db(db.hostel_mapping.hostel==catid).select()
	# value+=[{catid:"Unknown"}]
	# return value[catid]
	if value==[]:
		return "Unknown"
	else:
		return value[0]["hostel_name"]

