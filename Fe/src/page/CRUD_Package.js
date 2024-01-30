import React, { useEffect, useState } from "react";
import { DashboardLayout } from "../layout";
import { HostApi, ServiceApi } from "../api";
import {
  Box,
  Container,
  MenuItem,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import DatePicker from "react-multi-date-picker";
import DatePanel from "react-multi-date-picker/plugins/date_panel";
import TimePicker from "react-multi-date-picker/plugins/time_picker";
const CRUD_Package = () => {
  const today = new Date();
  const tomorrow = new Date();

  tomorrow.setDate(tomorrow.getDate() + 1);

  const [values, setValues] = useState([today, tomorrow]);
  const [locationList, setLocationList] = useState([]);
  const [locationValue, setLocationValue] = useState("");
  const getListAService = async () => {
    try {
      const res = await ServiceApi.getListservice();
      console.log("🚀 ~ getListAService ~ res:", res);
    } catch (error) {
      alert(error);
    }
  };
  const getListLocation = async () => {
    try {
      const res = await HostApi.getListLocation();
      setLocationList(res);
    } catch (error) {
      alert(error);
    }
  };
  useEffect(() => {
    getListAService();
    getListLocation();
  }, []);

  return (
    <div>
      {" "}
      <DashboardLayout>
        <h1>Create Package</h1>
        <Container
          maxWidth="lg"
          sx={{
            mt: 3,
          }}
        >
          <Stack direction={"column"} spacing={1}>
            {/* name */}
            <Stack direction={"column"}>
              <p>Name:</p>
              <TextField id="outlined-basic" label="Name" variant="outlined" />
            </Stack>
            {/* Description */}
            <Stack direction={"column"}>
              <p>Description:</p>
              <TextField
                id="outlined-basic"
                label="Description"
                variant="outlined"
              />
            </Stack>
            {/* capacity */}
            <Stack direction={"column"}>
              <p>capacity:</p>
              <TextField
                id="outlined-basic"
                label="capacity"
                variant="outlined"
                type="number"
              />
            </Stack>
            {/* capacity */}
            <Stack direction={"column"}>
              <p>Time Slot:</p>
              <DatePicker
                multiple
                value={values}
                onChange={setValues}
                plugins={[<DatePanel />, <TimePicker position="bottom" />]}
                format="MM/DD/YYYY HH:mm:ss"
                minDate={new Date()}
                style={{
                  height: 50,
                  width: "99%",
                }}
              />
            </Stack>
            {/* Location */}
            <Stack direction={"column"}>
              <p>Location:</p>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                label="Location"
                value={locationValue}
                onChange={(e) => setLocationValue(e.target.value)}
              >
                {locationList.length > 0 &&
                  locationList.map((item, index) => (
                    <MenuItem value={item?.name} key={index}>
                      {item?.name}
                    </MenuItem>
                  ))}
              </Select>
            </Stack>
          </Stack>
        </Container>
      </DashboardLayout>
    </div>
  );
};

export default CRUD_Package;
