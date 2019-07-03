#_______________________________________________________________________
# Function   : Change integer number(MAX = 255) to hex string
#
# Input      : the integer number (MAX = 255)
#
# Output     : 8-bit hex string
#_______________________________________________________________________

def NumtoStrhex(num):
    hi = num / 16
    hi = str(hex(hi))
    lo = num % 16
    lo = str(hex(lo))
    num = hi[2] + lo[2]
    return num