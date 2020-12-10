class Persona:
    def __init__(self, id: int, nombre: str, apellido: str, edad: int, genero: str, estadoCivil: str, idConyuge: int):
        self.__id = id
        self.__nombre = nombre
        self.__apellido = apellido
        self.__edad = edad
        self.__genero = genero
        self.__estadoCivil = estadoCivil
        self.__idConyugue = idConyuge

    def setID(self, id: int):
        self.__id = id

    def setNombre(self, nombre: str):
        self.__nombre = nombre

    def setApellido(self, apellido: str):
        self.__apellido = apellido

    def setEdad(self, edad: int):
        self.__edad = edad

    def setGenero(self, genero: str):
        self.__genero = genero

    def setEstadoCivil(self, estadoCivil: str):
        self.__estadoCivil = estadoCivil

    def setIDConyuge(self, idConyuge: int):
        self.__idConyugue = idConyuge

    def getID(self):
        return self.__id

    def getNombre(self):
        return self.__nombre

    def getApellido(self):
        return self.__apellido

    def getEdad(self):
        return self.__edad

    def getGenero(self):
        return self.__genero

    def getEstadoCivil(self):
        return self.__estadoCivil

    def getIDConyuge(self):
        return self.__idConyugue

def readFile(file_path: str):
    file_info = []
    with open(file_path, 'r') as file:
        for line in file.readlines():
            # Se quitan los enters
            line = line.replace('\n', '')
            # Se separa la información y se lo pone en un arreglo
            file_info.append(line.split(' '))
    return file_info

def writeFile(file_path: str, array):
    with open(file_path, 'w') as file:
        file.writelines(array)

def getPersonaString(persona):
    # Se le da formato
    info_per = str(persona.getID()) + '  '
    info_per += persona.getNombre() + ' '
    info_per += persona.getApellido() + ' '
    info_per += str(persona.getEdad()) + ' '
    info_per += persona.getGenero() + ' '
    info_per += persona.getEstadoCivil() + ' '
    info_per += str(persona.getIDConyuge()) + '\n'
    return info_per


def getMenores30(personas):
    menores30 = []
    for persona in personas:
        if persona.getEdad() < 30:
            menores30.append(getPersonaString(persona))

    menores30.insert(0, str(len(menores30)) + '\n')
    return menores30

def getHombresSolteros(personas):
    hombresSolteros = []
    for persona in personas:
        if persona.getGenero() == 'M' and 18 <= persona.getEdad() <= 20 and persona.getEstadoCivil() == 'S':
            hombresSolteros.append(getPersonaString(persona))

    hombresSolteros.insert(0, str(len(hombresSolteros)) + '\n')
    return hombresSolteros

def getCasadosString(matrimonios):
    matri = [str(len(matrimonios)) + '\n']

    for matrimonio in matrimonios:
        info_matri = matrimonio[0].getApellido() + ' '
        info_matri += matrimonio[1].getApellido() + ' '
        info_matri += str((matrimonio[0].getEdad() + matrimonio[1].getEdad())/2) + '\n'
        matri.append(info_matri)

    return matri

def getMatrimonios(personas):
    matrimonios = []

    for persona1 in personas:
        if persona1.getEstadoCivil() == 'C':
            for persona2 in personas:
                if persona2.getEstadoCivil() == 'C' and persona1.getIDConyuge() == persona2.getID() and [persona1, persona2] not in matrimonios and [persona2, persona1] not in matrimonios:
                    estaEn = False
                    for i in range(len(matrimonios)):
                        if (persona1.getEdad() + persona2.getEdad())/2 < (matrimonios[i][0].getEdad() + matrimonios[i][1].getEdad())/2:
                            matrimonios.insert(i, [persona1, persona2])
                            estaEn = True
                            break

                    if not estaEn:
                        matrimonios.append([persona1, persona2])

    return getCasadosString(matrimonios)

if __name__ == '__main__':
    info_personas = readFile('database_people.txt')

    # Este arreglo tendra todos los objectos Person que tendra cada una de las personas del database
    personas = []

    # se crea cada objeto persona y se lo agrega al arreglo
    for info in info_personas:
        personas.append(Persona(int(info[0]), info[1], info[2], int(info[3]), info[4], info[5], int(info[6])))

    menores30 = getMenores30(personas)
    writeFile('HombresYMujeresMenoresA30.txt', menores30)

    hombresSolteros = getHombresSolteros(personas)
    writeFile('HombresSolteros.txt', hombresSolteros)

    matrimonios = getMatrimonios(personas)
    writeFile('Matrimonios.txt', matrimonios)