
import { createContext, useState } from "react";
import { ConfigProvider } from "antd";
import { useDate } from "../../Hooks/All/useDate";
import { Host } from "./Host/Host";
import { Admin } from "./Admin/Admin";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Login } from "./Login/Login";
import { message } from "antd";

import axios from "axios";

export const Data = createContext();
export const Body = (props) => {
  const user = props.user,
    setUser = props.setUser;

  const { getCurrentDate, GetWeek } = useDate();
  const [currentNoti, setCurrentNoti] = useState(false);
  setTimeout(() => {
    if (user !== null && user !== undefined && user != {}) {
      axios
        .post(
          `http://localhost:8080/booking/auth/authenticate`, user
        )
        .then((response) => {
          console.log(user);
          return response.data; 
        })
        .then((fetchUser) => {
          if (!fetchUser.status) {
            setUser(null);
            setRole(null);

            sessionStorage.removeItem("user");
            !currentNoti && message.error("Your account have been disabled");
            setCurrentNoti(true);
          }
        })
        .catch((error) => console.log(error));
    }
  }, 1000 * 60 * 15);

  //Create new client
  const client = new QueryClient({
    defaultOptions: {
      queries: {
        refetchOnWindowFocus: false
      },
    },
  });


  const [selectedDate, setSelectedDate] = useState(getCurrentDate());
  const [selectedWeek, setSelectedWeek] = useState(GetWeek(selectedDate));

  const startRole =
    sessionStorage.user === undefined
      ? null
      : user.role;
  const [role, setRole] = useState(startRole);

  const [menuOpt, setMenuOpt] = useState("default");

  return (
    <QueryClientProvider client={client}>
      <Data.Provider
        value={{
          menuOpt,
          setMenuOpt,
          selectedDate,
          setSelectedDate,
          selectedWeek,
          setSelectedWeek,
          user,
          setUser,
          setRole,
        }}
      >
        <ConfigProvider theme={{ token: { colorPrimary: "#F15A25" } }}>
          {
            
            role === "HOST" ? (
              <>
                <Host setMenuOpt={setMenuOpt} menuOpt={menuOpt}/>
              </>
            ) :
            role === "ADMIN" ? (
              <Admin setMenuOpt={setMenuOpt} menuOpt={menuOpt} />
            ) : (
              <>
                <Login />
              </>
            )
          }
        </ConfigProvider>
      </Data.Provider>
    </QueryClientProvider>
  );
};
