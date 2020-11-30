class Character:
    '''
    The base Character class, which all others are based off
    '''
    def __init__(self, name, strength):
        self._verifyName(name)
        self._verifyStrength(strength)

    def _verifyName(self, name):
        if type(name) != str:
            print("type ERROR")
        self._name = name

    def _verifyStrength(self, strength):
        if strength >= 5:
            strength = 5.0
        elif strength <= 0:
            strength = 0.0
        elif strength % 1 == 0:
            strength = float(strength)
        if type(strength) != float:
            print("type ERROR")
            
        self._strength = strength

    @property
    def name(self):
        return self._name

    @property
    def strength(self):
        return self._strength

    @name.setter
    def name(self, name):
        self._verifyName(name)

    @strength.setter
    def strength(self, strength):
        self._verifyStrength(strength)

class Orc(Character):
    '''The Orc class'''
    def __init__(self, name, strength, weapon):
        self._verifyName(name)
        self._verifyStrength(strength)
        self._verifyWeapon(weapon)

    def __str__(self):
        return "%s %.1f %s" % (self.name, self.strength, self.weapon)
    
    def __gt__(self, other):
        '''
        Overloading the > operator - orc version
        '''
        if isinstance(other, Orc):
            if self.weapon > other.weapon:
                return True
            else:
                return self.strength > other.strength
        else:
            if self.weapon:
                return self.strength > other.strength

    # Getters -----

    @property
    def weapon(self):
        return self._weapon

    # ----------

    # Setters -----
    
    @weapon.setter
    def weapon(self, weapon):
        self._verifyWeapon(weapon)

    # ----------

    # Verification functions ---

    def _verifyWeapon(self, weapon):
        if type(weapon) != bool:
            print("type ERROR")
        self._weapon = weapon

    # ---------------

    def fight(self, other):
        '''
        Allows orcs to fight other orcs
        '''
        if isinstance(other, Orc) or isinstance(other, Archer):
            if self > other:
                self.strength += 1
                print(self)
            elif other > self:
                other.strength += 1
                print(other)
            else:
                self.strength -= 0.5
                other.strength -= 0.5
        else:
            print("type ERROR")


class Archer(Character):
    '''The Archer class'''
    def __init__(self, name, strength, kingdom):
        self._verifyName(name)
        self._verifyStrength(strength)
        self._verifyKingdom(kingdom)
    
    def __str__(self):
        s = self.name + " " +  str(self.strength) + " " + self.kingdom
        return s

    def __gt__(self, other):
        '''
        Overloading the > operator - archer/knight version
        '''
        if isinstance(other, Orc):
            if not other.weapon:
                return True
            else:
                return self.strength > other.strength
        else:
            print("type ERROR")

    def fight(self, other):
        if not isinstance(other, Orc):
            print("fight ERROR")
        else:
            if self > other:
                self.strength += 1
                print(self)
            elif other > self:
                other.strength += 1
                print(other)
            else:
                self.strength -= 0.5
                other.strength -= 0.5

    # Getters

    @property
    def kingdom(self):
        return self._kingdom

    # Setters
    @kingdom.setter
    def kingdom(self, x):
        self._verifyKingdom(x)

    # Verification functions

    def _verifyKingdom(self, kingdom):
        if type(kingdom) != str:
            print("type ERROR")
        self._kingdom = kingdom
            

class Knight(Archer):
    '''The Knight class - extends the Archer class'''
    def __init__(self, name, strength, kingdom, archers_list):
        self._verifyName(name)
        self._verifyStrength(strength)
        self._verifyKingdom(kingdom)
        self._archers_list = []
        self._verifyArchersList(archers_list)

    def __str__(self):
        s = self.name + " " + str(self.strength) + " " + self.kingdom + " [" + ', '.join([str(i) for i in self.archers_list]) + "]"
        return s

    # Getters

    @property
    def archers_list(self):
        return self._archers_list

    # Setters

    @archers_list.setter
    def archers_list(self, x):
        self._verifyArchersList(x)

    # Verification functions

    def _verifyArchersList(self, archers_list):
        if type(archers_list) == list:
            for i in archers_list:
                if isinstance(i, Archer) and not isinstance(i, Knight):
                    if i.kingdom == self.kingdom:
                        self._archers_list.append(i)
                else:
                    print("type ERROR")
        else:
            print("type ERROR")

if __name__ == "__main__":
    orc = Orc("Ogrorg", 4.3, True)
    orc2 = Orc("Ogrorg", 4.2, True)

    orc.name = "orc"

    print(orc, orc2)

    orc.fight(orc2)

    print(orc, orc2)

    archers = [Archer("Archer "+str(i+1), 3.6, "Arnor") for i in range(10)]

    print([str(i) for i in archers])

    k1 = Knight("Aragorn", 5.0, "Arnor", archers)

    print(k1)

    k1.fight(orc)

    print(k1, orc)