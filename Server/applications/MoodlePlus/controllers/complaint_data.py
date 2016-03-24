def index():
    if len(request.args)<1:
        raise HTTP(404)
    return request.args[0]

def get_complaint_details():
    if ("complaint_id" in request.vars and auth.is_logged_in()):
        compid = request.vars.complaint_id
        pref=None
        if compid[:2]=="i_":
            pref=db(db.indiv_complaints.complaint_id==compid).select()
            # Individual complaint
        elif (compid[:2]=="in"):
            pref=db(db.insti_complaints.complaint_id==compid).select()
            # Insti complaint
        elif (compid[:2]=="h_"):
            pref=db(db.hostel_complaints.complaint_id==compid).select()
            # Hostel complaint
        else:
            return dict(Details=[])
        if pref==[]:
            pref=[[]]
        return dict(Details=pref[0])
    return dict(Details=[])