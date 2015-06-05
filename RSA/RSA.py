import random

def divM(M,n):
    M = str(M)
    tamM = len(M)
    tamN = len(str(n))
    newM = []
    ini = 0

    #print(tamN,tamM)
    #print(M)
    
    while ini < tamM:
        fin = ini + tamN
        if (fin>tamM):
            fin=tamM
        if(int(M[ini:fin]) > n):
            fin = fin - 1
            
        #print(M[ini:fin]," menor que ",n)
        newM.append(int(M[ini:fin]))
        ini=fin + 1
                          
        
    return newM

def divMsergio(M,n):
    M = str(M)
    tamM = len(M)
    tamN = len(str(n))
    newM = []
    i = 0

    print(tamN,tamM)
    print(M)
    tam = 0
    while i < tamM:

        print(M[i*(tamN-1):(i+1)*(tamN-1)])
        newM.append(int(M[i*(tamN-1):(i+1)*(tamN-1)]))
        tam += len(M[i*(tamN-1):(i+1)*(tamN-1)])
        if(tam == tamM):
            i = tamM+1
        else:
            i = i+1
    return newM



def getPrimes(start, stop):
    if start >= stop:
        return []

    primes = [2]

    for n in range(3, stop + 1, 2):
        for p in primes:
            if n % p == 0:
                break
        else:
            primes.append(n)

    while primes and primes[0] < start:
        del primes[0]

    return primes


def EEA(a, b):
    x,y, u,v = 0,1, 1,0
    while a != 0:
        q, r = b//a, b%a
        m, n = x-u*q, y-v*q
        b,a, x,y, u,v = a,r, u,v, m,n
    gcd = b
    return gcd, x, y


def keyGenerator():
    """
    #EJEMPLO PROFESOR
    primes = getPrimes(10000, 99999)

    (p, q) = (primes[random.randint(0,len(primes))], primes[random.randint(0,len(primes))])

    p = 47
    q = 71
    n = p*q

    fi = (p-1)*(q-1)

    gcd = 0

    while gcd != 1:
        #e = random.randint(1, fi)
        e = 79
        (gcd, x, d) = EEA(fi, e)
    keys = {"public": (e, n), "private": (d, n)}

    return keys

    """

    primes = getPrimes(10000, 100000)
    (p, q) = (primes[random.randint(0, len(primes)-1)], primes[random.randint(0, len(primes)-1)])
    n = p*q
    fi = (p-1)*(q-1)
    print("p:",p,"q:",q)
    print("n:",n,"fi",fi)
    gcd = 0
    d = -1
    while gcd != 1 or d < 0:
        e = random.randint(2, fi-1)
        #print ("e:",e)
        (gcd, d, y) = EEA(e, fi)

    #print ("gcd:",gcd,"x:", d,"y:", y)
    #print((e*d)%fi)
    if d < 0:
        keys = {"public": (e, n), "private": (d, n)}
    else:
        keys = {"public": (e, n), "private": (d, n)}

    return keys



def encryption(m , key):
    m = divM(m,keys.get("public")[1])
    j = 0
    c = ''
    ci = []
    for s in m:
        ci.append(pow(s,key,keys.get("public")[1]))
        c += str(ci[j])
        j += 1
    return m, ci, long(c)

def decryption(c , key):
    m = c
    j = 0
    c = ''
    ci = []
    for s in m:
        ci.append(pow(s,key,keys.get("public")[1]))
        c += str(ci[j])
        j += 1
    return m, ci, long(c)



def mesajeASC(m):
    mesASC = ""
    for i in m:
        mesASC = mesASC + str(ord(i))
    return int(mesASC)


keys = keyGenerator()
print(keys)
mo = 6882326879666683
print("Mensaje original",mo)
c = encryption(mo, keys.get("public")[0])[1]
print("Encryp",c)

m = decryption(c, keys.get("private")[0])[2]
print("Decript",m)
