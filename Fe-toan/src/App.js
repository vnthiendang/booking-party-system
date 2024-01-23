import "./App.css";
import { Nav } from "./Components/Nav/Nav";
import { Body } from "./Components/Body/Body";
import { useState } from "react";
import { privatePage } from "./Routes";
import {
  Routes,
  Route
} from "react-router-dom"



function App() {
  let startUser = null
  if (sessionStorage.user !== undefined) {
    try {
      const firstParse = JSON.parse(sessionStorage.user);
      const secondParse = JSON.parse(atob(firstParse.info));
      startUser = {
        name: firstParse.name,
        email: secondParse.email,
        role: secondParse.role,
        phone: secondParse.phone,
      };
    } catch (error) {
      console.log("Error parsing sessionStorage.user:", error);
    }
  }
  const [user, setUser] = useState(startUser);

  return (
    

    // <Routes>
    //     {privatePage.map((route, index) => (
    //       <Route>
    //         key={index}
    //         path={route.path}
    //         element={
    //           <>
    //           <Nav
    //             user={user}
    //             setUser={setUser}
    //           />
    //           <Body
    //             user={user}
    //             setUser={setUser}
    //           />
    //         </>
    //         }
    //       </Route>
    //     ))}
    // </Routes>

    <>
              <Nav
                user={user}
                setUser={setUser}
              />
              <Body
                user={user}
                setUser={setUser}
              />
            </>
  );
}

export default App;
