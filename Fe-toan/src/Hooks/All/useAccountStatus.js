export const useAccountStatus = () => {
  const validStatus = (finalUser, setUser, setRole, setDisableAccount) => {
    if (finalUser.status === false) {
      setDisableAccount(true);
    } else {
      setUser(finalUser);
      setRole(finalUser.role);
      const encodeInfo = {
        name: finalUser.name,
        email: finalUser.email,
        phone: finalUser.phone,
        role: finalUser.role,
      };
      const saveToSession = {
        name: finalUser.name,
        info: btoa(JSON.stringify(encodeInfo))
      }
      sessionStorage.setItem("user", JSON.stringify(saveToSession));
    }
  };

  return validStatus;
};
