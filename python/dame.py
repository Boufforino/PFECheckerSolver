import numpy as np
import random
import math

def init():
    res = np.array([[-1,2,-1,2,-1,2,-1,2],
             [2,-1,2,-1,2,-1,2,-1],
             [-1,2,-1,2,-1,2,-1,2],
             [0,-1,0,-1,0,-1,0,-1],
             [-1,0,-1,0,-1,0,-1,0],
             [1,-1,1,-1,1,-1,1,-1],
             [-1,1,-1,1,-1,1,-1,1],
             [1,-1,1,-1,1,-1,1,-1]]
             )
    return res

plateau = init()

def coupFollowUpJ2(plateau,x,y):
    listC = []
    if (y - 1 >= 0):
        if (x + 1 < 8):
            caseP = plateau[x+1][y-1]
            if (caseP == 1):
                if (y - 2 >= 0):
                    if (x + 2 < 8):
                        if  plateau[x+2][y-2] == 0:
                            listC.append([(x,y),(x+2,y-2)])
                            resAdd = coupFollowUpJ2(plateau,x+2,y-2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x+2,y-2),(resAdd[i])])
    if (y + 1 < 8):
        if (x + 1 < 8):
            caseP = plateau[x+1][y+1]
            if (caseP == 1):
                if (y + 2 < 8):
                    if (x + 2 < 8):
                        if  plateau[x+2][y+2] == 0:
                            listC.append([(x,y),(x+2,y+2)])
                            resAdd = coupFollowUpJ2(plateau,x+2,y+2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x+2,y-2),(resAdd[i])])
    return listC

def coupFollowUpJ1(plateau,x,y):
    listC = []
    if (y - 1 >= 0):
        if (x - 1 >=0):
            caseP = plateau[x-1][y-1]
            if (caseP == 2):
                if (y - 2 < 8):
                    if (x - 2 >= 0):
                        if  plateau[x-2][y-2] == 0:
                            listC.append([(x,y),(x-2,y-2)])
                            resAdd = coupFollowUpJ1(plateau,x-2,y-2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x-2,y-2),(resAdd[i])])
    if (y + 1 < 8):
        if (x - 1 >=0):
            caseP = plateau[x-1][y+1]
            if (caseP == 2):
                if (y + 2 < 8):
                    if (x - 2 >= 0):
                        if  plateau[x-2][y+2] == 0:
                            listC.append([(x,y),(x-2,y+2)])
                            resAdd = coupFollowUpJ1(plateau,x-2,y+2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x-2,y-2),(resAdd[i])])
    return listC






def coupJ2(plateau,x,y):
    listC = []
    if (y - 1 >= 0):
        if (x + 1 < 8):
            caseP = plateau[x+1][y-1]
            if (caseP == 0):
                listC.append([(x,y),(x+1,y-1)])
            elif (caseP == 1):
                if (y - 2 >= 0):
                    if (x + 2 < 8):
                        if  plateau[x+2][y-2] == 0:
                            listC.append([(x,y),(x+2,y-2)])
                            resAdd = coupFollowUpJ2(plateau,x+2,y-2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x+2,y-2),(resAdd[i])])
    if (y + 1 < 8):
        if (x + 1 < 8) :
            caseP = plateau[x+1][y+1]
            if (caseP == 0):
                listC.append([(x,y),(x+1,y+1)])
            elif (caseP == 1) :
                if (y + 2 <8):
                    if (x + 2 < 8):
                        if  plateau[x+2][y+2] == 0:
                            listC.append([(x,y),(x+2,y+2)])
                            resAdd = coupFollowUpJ2(plateau,x+2,y+2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x+2,y+2),(resAdd[i])])
    return listC

def coupJ1(plateau,x,y):
    listC = []
    if (y - 1 >= 0):
        if (x - 1 >= 0):
            caseP = plateau[x-1][y-1]
            if (caseP == 0):
                listC.append([(x,y),(x-1,y-1)])
            elif (caseP == 2):
                if (y - 2 >= 0):
                    if (x - 2 >= 0):
                        if  plateau[x-2][y-2] == 0:
                            listC.append([(x,y),(x-2,y-2)])
                            resAdd = coupFollowUpJ1(plateau,x-2,y-2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x-2,y-2),(resAdd[i])])
    if (y + 1 < 8):
        if (x - 1 >= 0):
            caseP = plateau[x-1][y+1]
            if (caseP == 0):
                listC.append([(x,y),(x-1,y+1)])
            elif (caseP == 2) :
                if (y + 2 < 8):
                    if (x - 2 >= 0):
                        if  plateau[x-2][y+2] == 0:
                            listC.append([(x,y),(x-2,y+2)])
                            resAdd = coupFollowUpJ1(plateau,x-2,y+2)
                            for i in range(0,len(resAdd)):
                                listC.append([(x,y),(x-2,y+2),(resAdd[i])])
    return listC



#print(coupJ2(plateau,2,7))
#coupJ1(plateau,5,4)

def conditionVictoire(plateau):
    eliminationJoueur1 = not(1 in plateau)
    eliminationJoueur2 = not(2 in plateau)
    return (1 in plateau[0]) or (2 in plateau[7]) or eliminationJoueur1 or eliminationJoueur2


def listCoupOriginePossibleJ1(plateau):
    res = []
    for i in range(0,8):
        for j in range(0,8):
            if (plateau[i][j] == 1):
                x = i
                y = j
                if (y - 1 >= 0):
                    if (x - 1 >= 0):
                        caseP = plateau[x-1][y-1]
                        if (caseP != 1):
                            res.append((x,y))
                            continue
                if (y + 1 < 8):
                    if (x - 1 < 8):
                        caseP = plateau[x-1][y+1]
                        if (caseP != 1):
                            res.append((x,y))
    return res


def listCoupOriginePossibleJ2(plateau):
    res = []
    for i in range(0,8):
        for j in range(0,8):
            if (plateau[i][j] == 2):
                x = i
                y = j
                if (y - 1 >= 0):
                    if (x + 1 < 8):
                        caseP = plateau[x+1][y-1]
                        if (caseP != 2):
                            res.append((x,y))
                            continue
                if (y + 1 < 8):
                    if (x - 1 < 8):
                        caseP = plateau[x+1][y+1]
                        if (caseP != 2):
                            res.append((x,y))
    return res



def enleverSouffletteJ1(plateau):
    res = []
    for i in range(0,8):
        for j in range(0,8):
            x = i
            y = j
            if (y - 1 >= 0):
                if (x - 1 >= 0):
                    caseP = plateau[x-1][y-1]
                    if (caseP == 2):
                        if (y - 2 >= 0):
                            if (x - 2 >= 0):
                                if  plateau[x-2][y-2] == 0:
                                    res.append((x,y))
                                    continue
            if (y + 1 < 8):
                if (x - 1 >= 0):
                    caseP = plateau[x-1][y+1]
                    if (caseP == 2):
                        if (y + 2 < 8):
                            if (x - 2 >= 0):
                                if  plateau[x-2][y+2] == 0:
                                    res.append((x,y))
    return res


def enleverSouffletteJ2(plateau):
    res = []
    for i in range(0,8):
        for j in range(0,8):
            x = i
            y = j
            if (y - 1 >= 0):
                if (x + 1 < 8):
                    caseP = plateau[x+1][y-1]
                    if (caseP == 1):
                        if (y - 2 >= 0):
                            if (x + 2 < 8):
                                if  plateau[x+2][y-2] == 0:
                                    res.append((x,y))
                                    continue
            if (y + 1 < 8):
                if (x + 1 <8):
                    caseP = plateau[x+1][y+1]
                    if (caseP == 1):
                        if (y + 2 < 8):
                            if (x + 2 < 8):
                                if  plateau[x+2][y+2] == 0:
                                    res.append((x,y))
    return res

def effectuerCoup(plateau,joueur,enchainement):
    if  enchainement == [] :
        return plateau
    else :
        origin = enchainement[0]
        try :
            destination =enchainement[1]
        except IndexError :
            tab = [item for sublist in enchainement for item in sublist]
            origin = tab[0]
            destination = tab[1]
        plateau_clone = np.copy(plateau)
        plateau_clone[origin[0]][origin[1]] = 0
        plateau_clone[destination[0]][destination[1]] = joueur
        jump = (destination[0] - origin[0])
        if (jump == 2) :
            if (destination[1] > origin[1]) :
                plateau_clone[origin[0]+1][origin[1]+1] = 0
            else :
                plateau_clone[origin[0]+1][origin[1]-1] = 0
        elif (jump==-2):
            if (destination[1]> origin[1]) :
                plateau_clone[origin[0]-1][origin[1]+1] = 0
            else :
                plateau_clone[origin[0]-1][origin[1]-1] = 0
        return effectuerCoup(plateau_clone,joueur,enchainement[2:])



################################################################################

def partie_full_random(plateau,tour):
    if tour :
        #joueur1
        soufflete  = enleverSouffletteJ2(plateau)
        plateau_clone = np.copy(plateau)
        for s in soufflete:
            plateau_clone[s[0]][s[1]] = 0
        listCoup = []
        listCoupOri = listCoupOriginePossibleJ1(plateau)
        for c in listCoupOri:
            listCoup.append(coupJ1(plateau_clone,c[0],c[1]))
        listCoupF = [item for sublist in listCoup for item in sublist]
        if listCoupF == [] :
            fin = True
        else :
            getCoups = random.choice(listCoupF)
            plateau_clone = effectuerCoup(plateau_clone,1,getCoups)
            fin = conditionVictoire(plateau_clone)
    else :
        #joueur2
        soufflete  = enleverSouffletteJ1(plateau)
        plateau_clone = np.copy(plateau)
        for s in soufflete:
            plateau_clone[s[0]][s[1]] = 0
        listCoup = []
        listCoupOri = listCoupOriginePossibleJ2(plateau)
        for c in listCoupOri:
            listCoup.append(coupJ2(plateau_clone,c[0],c[1]))
        listCoupF = [item for sublist in listCoup for item in sublist]
        if listCoupF == [] :
            fin = True
        else :
            getCoups = random.choice(listCoupF)
            plateau_clone = effectuerCoup(plateau_clone,2,getCoups)
            fin = conditionVictoire(plateau_clone)
    if fin :
        return tour
    else :
        return partie_full_random(plateau_clone,not tour)


################################################################################


FOLDERDATA = "./data"
FOLDERRESULT = "./result"
from PIL import Image
import os
def compressPlateau(plateau):
    res = ""
    for i in range(8):
        for j in range(8):
            if (plateau[i][j] != -1) :
                res = res +str(plateau[i][j])
    return res

def createImage(plateau,name):
    res = Image.new( 'RGB', (4,8), "black")
    pixels = res.load()
    for i in range(8):
        counterYpiexel = 0
        for j in range(8):
            if plateau[i][j] == 1 :
                pixels[counterYpiexel,i] = (0, 0, 250)
            if plateau[i][j] == 2 :
                pixels[counterYpiexel,i] = (0, 250, 0)
            if plateau[i][j] != -1 :
                counterYpiexel = counterYpiexel + 1
    res.save(name)
    return 1

def ecrireMeilleurCoups(plateauAvant,plateauApres):
    fileName = compressPlateau(plateauAvant)
    if not os.path.isfile("./data/" + fileName + ".bmp") :
        createImage(plateauAvant,"./data/" + fileName + ".bmp")
        createImage(plateauApres,"./result/" + fileName + ".bmp")
    return 1















###############################################################################


def monteCarlo(plateau,tour,listCoupF):
    score = 0
    score_max = -1
    tab = []
    for x in listCoupF:
        tab.append((x,0,0,0))
    cp = tab
    copie = np.copy(plateau)
    nbPartieSimulee= 0
    for i in range(10):
        for j in range(len(cp)):
            score = 0
            cj_stat = cp[j]
            cj = cj_stat[0]
            UCB = cj_stat[1]
            score_total = cj_stat[2]
            nbcoupjoue = cj_stat[3]
            plateau_clone = effectuerCoup(plateau,1,cj)
            if conditionVictoire(plateau_clone) :
                score = 1
            else :
                resPartieRand = partie_full_random(effectuerCoup(plateau,1,cj),not tour)
                if resPartieRand :
                    score = 1
            score_total = score_total + score
            nbcoupjoue = nbcoupjoue + 1
            nbPartieSimulee = nbPartieSimulee +1
            cp[j] = (cj,score_total/nbcoupjoue + 0.4*(math.sqrt(math.log(nbPartieSimulee)/nbcoupjoue)) ,score_total,nbcoupjoue)
    for i in range(100):
        cp_sorted = sorted(cp, key=lambda tup: tup[1])
        cj_stat = cp_sorted[-1]
        cj = cj_stat[0]
        UCB = cj_stat[1]
        score_total = cj_stat[2]
        nbcoupjoue = cj_stat[3]
        plateau_clone = effectuerCoup(plateau,1,cj)
        if conditionVictoire(plateau_clone) :
            score = 1
        else :
            resPartieRand = partie_full_random(effectuerCoup(plateau,1,cj),not tour)
            if resPartieRand :
                score = 1
        score_total = score_total + score
        nbcoupjoue = nbcoupjoue + 1
        nbPartieSimulee = nbPartieSimulee +1
        cp[j] = (cj,score_total/nbcoupjoue + 0.4*(math.sqrt(math.log(nbPartieSimulee)/nbcoupjoue)) ,score_total,nbcoupjoue)
    cp_sorted = sorted(cp, key=lambda tup: tup[1])
    cj = cp_sorted[-1][0]
    ecrireMeilleurCoups(plateau,effectuerCoup(plateau,1,cj))
    return cj


################################################################################




def partie(plateau,tour):
    if tour :
        #joueur1
        soufflete  = enleverSouffletteJ2(plateau)
        plateau_clone = np.copy(plateau)
        for s in soufflete:
            plateau_clone[s[0]][s[1]] = 0
        listCoup = []
        listCoupOri = listCoupOriginePossibleJ1(plateau)
        for c in listCoupOri:
            listCoup.append(coupJ1(plateau_clone,c[0],c[1]))
        listCoupF = [item for sublist in listCoup for item in sublist]
        if listCoupF == [] :
            fin = True
        else :
            getCoups = monteCarlo(plateau,tour,listCoupF)
            plateau_clone = effectuerCoup(plateau_clone,1,getCoups)
            fin = conditionVictoire(plateau_clone)
    else :
        #joueur2
        soufflete  = enleverSouffletteJ1(plateau)
        plateau_clone = np.copy(plateau)
        for s in soufflete:
            plateau_clone[s[0]][s[1]] = 0
        listCoup = []
        listCoupOri = listCoupOriginePossibleJ2(plateau)
        for c in listCoupOri:
            listCoup.append(coupJ2(plateau_clone,c[0],c[1]))
        listCoupF = [item for sublist in listCoup for item in sublist]
        if listCoupF == [] :
            fin = True
        else :
            getCoups = random.choice(listCoupF)
            plateau_clone = effectuerCoup(plateau_clone,2,getCoups)
            fin = conditionVictoire(plateau_clone)
    if fin :
        return tour
    else :
        return partie(plateau_clone,not tour)




for i in range(0,1000) :
    print(i)
    partie(plateau,True)



#print(souffletteJ2(plateau))
'''
def main():
    coupSuivant = True;
    tourJ1 = True
    while coupSuivant :
        if tourJ1 :
            listCoupOriginePossibleJ1(plateau)
            effectuerCoup(plateau,listCoup)
        if tourJ2 :
            listCoup2(plateau)
            effectuerCoup(plateau,coup)
        coupSuivant = conditionVictoire(plateau)
'''
