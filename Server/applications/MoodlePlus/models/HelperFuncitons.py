def GetIndivComplaints():
	if auth.is_logged_in():
		allcomp = db(db.indiv_complaints.username==auth.user.username).select()
		return allcomp
	return []

def GetAdminIndivComplaints():
	allcomp = db(db.indiv_complaints.admin_id==auth.user.username).select()
	return allcomp

def GetHostelComplaints():
	# TODO add preferences complaints
	allcomp = db(db.hostel_complaints.username==auth.user.username).select()
	return allcomp

def GetAdminHostelComplaints():
	# TODO add preferences complaints
	allcomp = db(db.hostel_complaints.admin_id==auth.user.username).select()
	return allcomp

def GetInstiComplaints():
	# TODO add preferences complaints
	allcomp = db(db.insti_complaints.username==auth.user.username).select()
	return allcomp

def GetAdminInstiComplaints():
	# TODO add preferences complaints
	allcomp = db(db.insti_complaints.admin_id==auth.user.username).select()
	return allcomp

def isadmin():
	# TODO: remove comment
	return len(db(db.admin_info.username==auth.user.username).select())>0
	# return True

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
	return len(db((db.users.username==auth.user.username) & (db.users.user_type==-1)).select())>0
	# return True

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

def GetNewCompId(x):
    # x=0 for indiv , 1 for hostel and 2 for insti
    table=None
    if x==0:
        table=db(db.indiv_complaints).select(orderby=~db.indiv_complaints.complaint_id)
    elif x==1:
        table=db(db.hostel_complaints).select(orderby=~db.hostel_complaints.complaint_id)
    else:
        table=db(db.insti_complaints).select(orderby=~db.insti_complaints.complaint_id)
    num=1
    if len(table)>0:
        cons = table[0]["complaint_id"]
        num = 1 + int(cons.split('_')[1])
    num = str(num)
    arr=["i_","h_","in_"]
    num = arr[x]+num
    return num

def GetHostelID(s):
    f = db(db.users.username==s).select()[0]["hostel"]
    return f 

def GetHostel(x):
	# Takes in complaint as input and returns hostel in string
	catid = x["hostel"]
	value = db(db.hostel_mapping.hostel_id==catid).select()
	# value+=[{catid:"Unknown"}]
	# return value[catid]
	if value==[]:
		return "Unknown"
	else:
		return value[0]["hostel_name"]

