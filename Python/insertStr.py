#_______________________________________________________________________
# Function   : insert substring into long string in a certain interval
#
# Input      : long string to be seperated
#              substring to be inserted
#              interval to be inserted
#
# Output     : file or stdout
#_______________________________________________________________________
def insertStr(longstr, substr, interval):
    f = open('Output.txt', 'w+')
    strlen = len(longstr)
    num = strlen / interval
    rest = strlen % interval
    loffset = 0
    for x in xrange(1,num):
        f.write(longstr[loffset:(loffset+interval)])
        f.write(substr)
        loffset = loffset + interval
    if rest == 0:
        f.write(longstr[loffset:(loffset+interval)])
    else:
        f.write(longstr[loffset:(loffset+interval)])
        f.write(substr)
        loffset = loffset + interval
        f.write(longstr[loffset:(loffset+rest)])
    f.close()
    return
	